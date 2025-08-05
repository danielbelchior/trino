# Trino S3 Event Listener

This plugin uploads Trino event logs to S3 using the trino-filesystem-s3 classes.

## Configuration

Add the following to your `event-listener.properties`:

```
event-listener.name=s3-event-listener
s3.bucket=<your-bucket-name>
```

## How it works

- Listens for Trino events (query created, completed, failed, split completed)
- Serializes event data and uploads logs to S3
- Uses `trino-filesystem-s3` for S3 integration
