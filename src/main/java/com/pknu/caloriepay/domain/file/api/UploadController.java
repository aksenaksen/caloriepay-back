package com.pknu.caloriepay.domain.file.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.file.application.ImageUploadService;
import com.pknu.caloriepay.domain.file.domain.ImageCategory;
import com.pknu.caloriepay.domain.file.dto.response.UploadResult;
import com.pknu.caloriepay.domain.meal.application.MealService;
import com.pknu.caloriepay.domain.meal.dto.FoodDto;
import com.pknu.caloriepay.domain.meal.dto.MealDto;
import com.pknu.caloriepay.domain.meal.external.MealImageAnalysisClient;
import com.pknu.caloriepay.global.dto.BaseRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadController {

    private final ImageUploadService imageUploadService;
    private final MealImageAnalysisClient mealImageAnalysisClient;
    private final MealService mealService;

    // 추후에 exercise 인 경우 분기
    @PostMapping("/")
    public ResponseEntity<BaseRes<MealDto>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") ImageCategory category,
            @AuthenticationPrincipal CurrentMemberInfo memberInfo) {
        UploadResult res = imageUploadService.fileUpload(file,category,memberInfo);
        List<FoodDto> foodList = mealImageAnalysisClient.analyze(res);
        MealDto result = mealService.save(res,foodList,memberInfo);
        return ResponseEntity.ok().body(BaseRes.success(result));
    }

}
