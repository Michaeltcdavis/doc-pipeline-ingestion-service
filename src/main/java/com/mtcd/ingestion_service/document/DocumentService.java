package com.mtcd.ingestion_service.document;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

    public void ingest(MultipartFile file) {
        //TODO: implement kafka publishing
    }

}
