package io.trino.plugin.s3event;

import io.trino.spi.eventlistener.EventListener;
import io.trino.spi.eventlistener.EventListenerFactory;

import java.util.Map;

public class S3EventListenerFactory implements EventListenerFactory {
    @Override
    public EventListener create(Map<String, String> config) {
        return new S3EventListener(config);
    }
}
