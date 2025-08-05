package io.trino.plugin.s3event;

import io.trino.spi.eventlistener.EventListener;
import io.trino.spi.eventlistener.EventListenerFactory;
import io.trino.spi.eventlistener.EventListenerPlugin;

import java.util.Map;

public class S3EventListenerPlugin implements EventListenerPlugin {
    @Override
    public String getName() {
        return "s3-event-listener";
    }

    @Override
    public EventListenerFactory getEventListenerFactory() {
        return new S3EventListenerFactory();
    }
}
