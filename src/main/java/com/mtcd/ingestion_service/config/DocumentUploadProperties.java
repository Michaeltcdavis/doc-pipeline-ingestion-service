package com.mtcd.ingestion_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("document.upload")
public record DocumentUploadProperties(List<String> allowedTypes) {
}
