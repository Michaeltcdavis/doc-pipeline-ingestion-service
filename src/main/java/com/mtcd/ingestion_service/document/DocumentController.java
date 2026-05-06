package com.mtcd.ingestion_service.document;

import com.mtcd.ingestion_service.config.DocumentUploadProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentUploadProperties uploadProperties;

    public DocumentController(DocumentService documentService, DocumentUploadProperties uploadProperties) {
        this.documentService = documentService;
        this.uploadProperties = uploadProperties;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDocument(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File must not be empty");
        }
        if (!uploadProperties.allowedTypes().contains(file.getContentType())) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Unsupported file type: " + file.getContentType());
        }
        if (file.getSize() > uploadProperties.maxFileSize()) {
            return ResponseEntity.status(HttpStatus.CONTENT_TOO_LARGE).body("File exceeds maximum file size of " + uploadProperties.maxFileSize() + " bytes");
        }
        try {
        documentService.ingest(file);
        return ResponseEntity.accepted().body("Document received: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to process document");
        }
    }

}
