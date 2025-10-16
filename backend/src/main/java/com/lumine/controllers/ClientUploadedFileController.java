package com.lumine.controllers;
import com.lumine.dtos.ClientUploadRequestDTO;
import com.lumine.dtos.ClientUploadResponseDTO;
import com.lumine.services.ClientUploadedFileService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;


@RestController
@RequestMapping("/api/files")
public class ClientUploadedFileController
{
    private final ClientUploadedFileService uploadedFileService;

    public ClientUploadedFileController(ClientUploadedFileService uploadedFileService)
    {
        this.uploadedFileService = uploadedFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ClientUploadResponseDTO> uploadFile (@Valid @ModelAttribute ClientUploadRequestDTO request)
    {
        try
        {
            ClientUploadResponseDTO response = uploadedFileService.uploadFile(request);
            return ResponseEntity.ok(response);

        } catch (Exception e)
        {
            // Optional: improve with custom exception handling
            return ResponseEntity.internalServerError().build();
        }
    }
}


