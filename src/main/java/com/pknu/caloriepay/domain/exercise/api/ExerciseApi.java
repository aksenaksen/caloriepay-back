package com.pknu.caloriepay.domain.exercise.api;

import com.pknu.caloriepay.domain.auth.dto.info.CurrentMemberInfo;
import com.pknu.caloriepay.domain.exercise.application.ExerciseRecorder;
import com.pknu.caloriepay.domain.exercise.application.ExerciseService;
import com.pknu.caloriepay.domain.exercise.application.ExerciseTypeFinder;
import com.pknu.caloriepay.domain.exercise.dto.in.ExerciseRecordRequest;
import com.pknu.caloriepay.domain.exercise.dto.out.ExerciseTypeResponse;
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

    private final ExerciseService exerciseService;

    @GetMapping("")
    public ResponseEntity<BaseRes<List<ExerciseTypeResponse>>> getAllExerciseName(){

        List<ExerciseTypeResponse> data = exerciseService.findTypes();
        return ResponseEntity.ok(BaseRes.success(data));
    }
    @PostMapping("/record")
    public ResponseEntity<BaseRes<Void>> postRecordExercise(@AuthenticationPrincipal CurrentMemberInfo info, @RequestBody @Valid ExerciseRecordRequest request){

        exerciseService.record(info.memberId(),request.title(),request.exercise());

        return ResponseEntity.ok(BaseRes.success(null));
    }
}
