package io.trino.plugin.s3event;

public class S3EventListenerConfig {
    // Add config fields as needed, e.g. bucket name, encryption, etc.
    private String bucket = "trino-logs";

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
