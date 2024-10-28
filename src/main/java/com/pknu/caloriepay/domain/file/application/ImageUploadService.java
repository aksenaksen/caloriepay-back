package com.pknu.caloriepay.domain.file.application;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.file.domain.FileStatus;
import com.pknu.caloriepay.domain.file.domain.ImageCategory;
import com.pknu.caloriepay.domain.file.domain.ImageFile;
import com.pknu.caloriepay.domain.file.domain.ImageFileRepository;
import com.pknu.caloriepay.domain.file.dto.response.UploadResult;
import com.pknu.caloriepay.domain.meal.dto.MealDto;
import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    private final FileStorageService fileStorageService;
    private final ImageFileRepository imageFileRepository;

    @Transactional
    public UploadResult fileUpload(MultipartFile file, ImageCategory category, CurrentMemberInfo memberInfo) {
        validateFile(file);
        try {
            String fileUrl = fileStorageService.uploadFile(file, category.toString());
            Long imageFileId = imageFileRepository.save(new ImageFile(fileUrl, category, FileStatus.UPLOADED)).getId();
            return new UploadResult(fileUrl,imageFileId);
        } catch (Exception e){
            throw new CustomException(ResCode.IMAGE_UPLOAD_FAILED);
        }
    }

    private void validateFile(MultipartFile file) {
        // 파일 형식 검사
        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("image/")) {
            throw new CustomException(ResCode.INVALID_IMAGE_FORMAT);
        }

        // 2. 파일 크기 검사 (5MB 제한)
        long maxSize = 5 * 1024 * 1024; // 5MB
        if (file.getSize() > maxSize) {
            throw new CustomException(ResCode.IMAGE_SIZE_EXCEEDED);
        }
    }
}
