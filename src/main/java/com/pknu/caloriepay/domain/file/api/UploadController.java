package com.pknu.caloriepay.domain.file.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.file.application.ImageUploadService;
import com.pknu.caloriepay.domain.file.domain.ImageCategory;
import com.pknu.caloriepay.domain.file.dto.response.UploadResult;
import com.pknu.caloriepay.domain.meal.dto.MealDto;
import com.pknu.caloriepay.global.dto.BaseRes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final ImageUploadService imageUploadService;

    public UploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping
    public ResponseEntity<BaseRes> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") ImageCategory category,
            @AuthenticationPrincipal CurrentMemberInfo memberInfo) {
        UploadResult res = imageUploadService.fileUpload(file,category,memberInfo);
    }

}
