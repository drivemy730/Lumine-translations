package com.lumine.services;

import com.lumine.dtos.UploadedFileTypeRequestDTO;
import com.lumine.dtos.UploadedFileTypeResponseDTO;
import com.lumine.helpers.ClientFileTypeCategory;
import com.lumine.models.Language;
import com.lumine.models.UploadedFileType;
import com.lumine.repositories.UploadedFileTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class UploadedFileTypeService {

    private final UploadedFileTypeRepository fileTypeRepository;

    public UploadedFileTypeService(UploadedFileTypeRepository fileTypeRepository) {
        this.fileTypeRepository = fileTypeRepository;
    }

    @Transactional(readOnly = true)
    public UploadedFileType getEntityById(Integer id) {
        return fileTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Language not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<UploadedFileTypeResponseDTO> getAllFileTypes() {
        return fileTypeRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UploadedFileTypeResponseDTO getFileTypeById(Integer id) {
        return fileTypeRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("File type not found"));
    }

    @Transactional
    public UploadedFileTypeResponseDTO createFileType(UploadedFileTypeRequestDTO request) {
        UploadedFileType fileType = new UploadedFileType(request.name());
        fileType.setBasePriceMultiplier(request.basePriceMultiplier());
        fileType.setDifficultyLevel(request.difficultyLevel());

        return toDto(fileTypeRepository.save(fileType));
    }

    @Transactional
    public UploadedFileTypeResponseDTO updateFileType(Integer id, UploadedFileTypeRequestDTO request) {
        UploadedFileType fileType = fileTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("File type not found"));

        fileType.setName(request.name());
        fileType.setBasePriceMultiplier(request.basePriceMultiplier());
        fileType.setDifficultyLevel(request.difficultyLevel());

        return toDto(fileTypeRepository.save(fileType));
    }

    @Transactional(readOnly = true)
    public BigDecimal getPriceMultiplier(ClientFileTypeCategory fileType) {
        return fileTypeRepository.findByName(fileType)
                .map(UploadedFileType::getBasePriceMultiplier)
                .orElse(BigDecimal.ONE);
    }

    private UploadedFileTypeResponseDTO toDto(UploadedFileType fileType) {
        return new UploadedFileTypeResponseDTO(
                fileType.getId(),
                fileType.getName(),
                fileType.getBasePriceMultiplier(),
                fileType.getDifficultyLevel()
        );
    }
}