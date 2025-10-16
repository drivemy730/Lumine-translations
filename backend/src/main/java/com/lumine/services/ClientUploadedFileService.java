package com.lumine.services;
import com.lumine.dtos.ClientUploadRequestDTO;
import com.lumine.dtos.ClientUploadResponseDTO;
import com.lumine.helpers.FileUploadedExceptionMessages;
import com.lumine.helpers.LanguageExceptionMessages;
import com.lumine.helpers.UserExceptionMessages;
import com.lumine.models.*;
import com.lumine.repositories.*;

import jakarta.persistence.EntityNotFoundException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class ClientUploadedFileService
{

    // ... otros métodos y atributos ...

    /**
     * uploadFile method uploads a file and returns metadata including a previewUrl.
     *
     * Notes - Features postponed for later implementation:
     * 1. Full URLs (HATEOAS) - Currently returning relative paths only for previewUrl.
     *    Future: Build full URLs on backend to simplify frontend.
     *
     * 2. Custom ErrorDTO - Currently using default Spring error handling.
     *    Future: Create unified error response class for consistent API errors.
     *
     * 3. Localization (i18n) - Validation and error messages are hardcoded in English.
     *    Future: Externalize messages for multi-language support.
     *
     * 4. Custom Validators (@FileType, @FileSize) - Skipped for now to reduce complexity.
     *    Future: Add reusable validation annotations for better file upload validation.
     *
     * 5. Additional DTO fields - For now, minimal fields in ClientUploadResponseDTO.
     *    Future: Add uploadTimestamp, status, or HATEOAS links if needed.
     */


    private final ClientUploadedFileRepository fileRepo;
    private final UserService userService;
    private final LanguageService languageService;
    private final UploadedFileTypeService uploadedFileTypeService;

    public ClientUploadedFileService(ClientUploadedFileRepository fileRepo,
                                     UserService userService,
                                     LanguageService languageService,
                                     UploadedFileTypeService uploadedFileTypeService)
    {
        this.fileRepo = fileRepo;
        this.userService = userService;
        this.languageService = languageService;
        this.uploadedFileTypeService = uploadedFileTypeService;
    }

    @Transactional
    public ClientUploadResponseDTO uploadFile(ClientUploadRequestDTO request) throws IOException
    {
        // 1. Process file
        MultipartFile file = request.file();
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String content = extractText(file.getInputStream(), fileExtension);
        int wordCount = countWords(content);

        // 2. Generate mock storage key
        String mockStorageKey = "mock/" + UUID.randomUUID() + "/" + file.getOriginalFilename();

        // 3. Fetch related entities
        User client = userService.getUserEntity(request.clientId());

        Language sourceLanguage = languageService.getEntityById(request.sourceLanguageId());

        Language targetLanguage = languageService.getEntityById(request.targetLanguageId());

        UploadedFileType fileType = uploadedFileTypeService.getEntityById(request.fileTypeId());

        // 4. Build and persist entity
        ClientUploadedFile uploadedFile = new ClientUploadedFile();
        uploadedFile.setFileName(file.getOriginalFilename());
        uploadedFile.setFileExtension(fileExtension);
        uploadedFile.setWordCount(wordCount);
        uploadedFile.setS3Key(mockStorageKey);
        uploadedFile.setClient(client);
        uploadedFile.setSourceLanguage(sourceLanguage);
        uploadedFile.setTargetLanguage(targetLanguage);
        uploadedFile.setFileType(fileType);

        fileRepo.save(uploadedFile);

        // 5. Return response DTO
        return new ClientUploadResponseDTO(
                uploadedFile.getId(),
                "/api/files/" + uploadedFile.getId() + "/preview",
                wordCount,
                uploadedFile.getFileName(),
                uploadedFile.getFileExtension()
        );
    }

    // ------------------- Internal Helpers -------------------

    private String extractText(InputStream inputStream, String fileExtension) throws IOException {
        String text;

        switch (fileExtension.toLowerCase()) {
            case "pdf":
                try (PDDocument document = PDDocument.load(inputStream)) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    text = stripper.getText(document);
                }
                break;

            case "docx":
                try (XWPFDocument doc = new XWPFDocument(inputStream);
                     XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {
                    text = extractor.getText();
                }
                break;

            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }

        return text;
    }

    private int countWords(String text) {
        return text == null || text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("Filename must contain an extension.");
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

    public ClientUploadedFile getFileEntity(UUID fileId) {
        return fileRepo.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("Uploaded file not found with ID: " + fileId));
    }

}
