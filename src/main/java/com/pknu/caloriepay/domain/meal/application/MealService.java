package com.pknu.caloriepay.domain.meal.application;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.file.domain.ImageFile;
import com.pknu.caloriepay.domain.file.domain.ImageFileRepository;
import com.pknu.caloriepay.domain.file.dto.response.UploadResult;
import com.pknu.caloriepay.domain.meal.dao.MealRepository;
import com.pknu.caloriepay.domain.meal.domain.Food;
import com.pknu.caloriepay.domain.meal.domain.Meal;
import com.pknu.caloriepay.domain.meal.dto.FoodDto;
import com.pknu.caloriepay.domain.meal.dto.MealDto;
import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;
import com.pknu.caloriepay.global.event.MealEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {

    private final ImageFileRepository imageFileRepository;
    private final MealRepository mealRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Transactional
    public MealDto save(UploadResult res, List<FoodDto> foodList, CurrentMemberInfo memberInfo) {
        LocalDateTime mealTime = imageFileRepository.findById(res.imageFileId())
                .orElseThrow(() -> new CustomException(ResCode.INTERNAL_SERVER_ERROR))
                .getUploadedAt();

        // Meal 객체 생성
        Meal meal = Meal.builder().memberId(memberInfo.memberId()).mealTime(mealTime).totalCalorie(0).mealImgUrl(res.imgUrl())
                .build();

        // Food 엔티티 리스트 생성
        List<Food> foods = foodList.stream().map(foodDto -> Food.builder()
                        .foodName(foodDto.getFoodName())
                        .foodImgUrl(foodDto.getFoodImgUrl())
                        .calorie(foodDto.getCalorie())
                        .protein(foodDto.getProtein())
                        .carbohydrate(foodDto.getCarbohydrate())
                        .fat(foodDto.getFat())
                        .build())
                .collect(Collectors.toList());

        // Meal에 Food 리스트 추가
        meal.addFoods(foods);

        // Meal과 관련된 Food 저장
        Meal result = mealRepository.save(meal);

        applicationEventPublisher.publishEvent(new MealEventDto(meal.getMemberId(), meal.getTotalCalorie()));

        return MealDto.from(result);
    }
}
