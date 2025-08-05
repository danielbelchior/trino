package io.trino.plugin.s3event;

import io.trino.spi.eventlistener.EventListener;
import io.trino.spi.eventlistener.QueryCompletedEvent;
import io.trino.spi.eventlistener.QueryCreatedEvent;
import io.trino.spi.eventlistener.SplitCompletedEvent;
import io.trino.spi.eventlistener.QueryFailureEvent;
import io.trino.filesystem.s3.S3OutputFile;
import io.trino.filesystem.s3.S3Context;
import io.trino.filesystem.s3.S3Location;
import io.trino.filesystem.encryption.EncryptionKey;
import software.amazon.awssdk.services.s3.S3Client;
import io.trino.memory.context.AggregatedMemoryContext;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.Map;

public class S3EventListener implements EventListener {
    private final S3Client s3Client;
    private final S3Context s3Context;
    private final String bucket;
    private final Executor uploadExecutor;
    private final Optional<EncryptionKey> encryptionKey;

    public S3EventListener(Map<String, String> config) {
        this.s3Client = S3Client.builder().build();
        this.s3Context = new S3Context(); // You may need to configure this with config
        this.bucket = config.getOrDefault("s3.bucket", "trino-logs");
        this.uploadExecutor = Executors.newFixedThreadPool(2);
        this.encryptionKey = Optional.empty(); // Add config for encryption if needed
    }

    private void uploadLog(String logType, String logContent) {
        String key = String.format("trino-logs/%s-%d.log", logType, System.currentTimeMillis());
        S3Location location = new S3Location(bucket, key);
        S3OutputFile outputFile = new S3OutputFile(uploadExecutor, s3Client, s3Context, location, encryptionKey);
        byte[] data = logContent.getBytes(StandardCharsets.UTF_8);
        try {
            outputFile.createOrOverwrite(data);
        } catch (IOException e) {
            // Handle error (log locally, etc.)
        }
    }

    @Override
    public void queryCreated(QueryCreatedEvent event) {
        uploadLog("queryCreated", event.toString());
    }

    @Override
    public void queryCompleted(QueryCompletedEvent event) {
        uploadLog("queryCompleted", event.toString());
    }

    @Override
    public void splitCompleted(SplitCompletedEvent event) {
        uploadLog("splitCompleted", event.toString());
    }

    @Override
    public void queryFailed(QueryFailureEvent event) {
        uploadLog("queryFailed", event.toString());
    }
}
