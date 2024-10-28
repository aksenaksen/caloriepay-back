package com.pknu.caloriepay.domain.exercise.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.exercise.application.ExerciseRecordService;
import com.pknu.caloriepay.domain.exercise.application.ExerciseTypeService;
import com.pknu.caloriepay.domain.exercise.dto.RequestExerciseRecordDto;
import com.pknu.caloriepay.domain.exercise.dto.ResponseExerciseTypeDto;
import com.pknu.caloriepay.global.dto.BaseRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseApi {

    private final ExerciseRecordService exerciseRecordService;
    private final ExerciseTypeService exerciseTypeService;

    @GetMapping("")
    public ResponseEntity<BaseRes<List<ResponseExerciseTypeDto>>> getAllExerciseName(){

        List<ResponseExerciseTypeDto> data = exerciseTypeService.getExerciseList();
        return ResponseEntity.ok(BaseRes.success(data));
    }
    @PostMapping("/record")
    public ResponseEntity<BaseRes<Void>> postRecordExercise(@AuthenticationPrincipal CurrentMemberInfo info, @RequestBody @Valid RequestExerciseRecordDto exerciseRecordDto){

        exerciseRecordService.recordExercise(info.memberId(),exerciseRecordDto.getTitle(),exerciseRecordDto.getExercise());

        return ResponseEntity.ok(BaseRes.success(null));
    }
}
