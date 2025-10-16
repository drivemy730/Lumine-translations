package com.lumine.controllers;

import com.lumine.dtos.UploadedFileTypeRequestDTO;
import com.lumine.dtos.UploadedFileTypeResponseDTO;
import com.lumine.helpers.ClientFileTypeCategory;
import com.lumine.services.UploadedFileTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/file-types")
public class UploadedFileTypeController {
    private final UploadedFileTypeService fileTypeService;

    public UploadedFileTypeController(UploadedFileTypeService fileTypeService)
    {
        this.fileTypeService = fileTypeService;
    }

    // 1. Get All File Types
    @GetMapping
    public List<UploadedFileTypeResponseDTO> getAllFileTypes() {
        return fileTypeService.getAllFileTypes();
    }

    // 2. Get Single File Type
    @GetMapping("/{id}")
    public UploadedFileTypeResponseDTO getFileType(@PathVariable Integer id) {
        return fileTypeService.getFileTypeById(id);
    }

    // 3. Create New File Type
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadedFileTypeResponseDTO createFileType(
            @RequestBody UploadedFileTypeRequestDTO request) {
        return fileTypeService.createFileType(request);
    }

    // 4. Update File Type
    @PutMapping("/{id}")
    public UploadedFileTypeResponseDTO updateFileType(
            @PathVariable Integer id,
            @RequestBody UploadedFileTypeRequestDTO request) {
        return fileTypeService.updateFileType(id, request);
    }

    // 5. Get Price Multiplier (Utility Endpoint)
    @GetMapping("/price-multiplier/{fileType}")
    public BigDecimal getPriceMultiplier(
            @PathVariable ClientFileTypeCategory fileType) {
        return fileTypeService.getPriceMultiplier(fileType);
    }
}