package com.mtcd.ingestion_service.document;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
public class DocumentService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.document-ingestion}")
    private String topic;

    public DocumentService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void ingest(MultipartFile file) throws IOException {
        DocumentEvent event = new DocumentEvent(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                file.getBytes()
        );
        kafkaTemplate.send(topic, event.fileName(), objectMapper.writeValueAsString(event))
                .whenComplete((result, e) -> {
                    if (e != null) {
                        System.err.println("Failed to publish document event: " + e.getMessage());
                    }
                });

    }

}
