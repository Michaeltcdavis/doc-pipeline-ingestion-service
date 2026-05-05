package com.mtcd.ingestion_service.document;

public record DocumentEvent(
        String fileName,
        String contentType,
        long sizeBytes,
        byte[] content
) {
}
