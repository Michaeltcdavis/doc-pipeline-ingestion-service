package com.mtcd.ingestion_service.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@ConfigurationProperties("document.upload")
@Validated
public record DocumentUploadProperties(@NotEmpty List<String> allowedTypes, @NotNull long maxFileSize) {
}
