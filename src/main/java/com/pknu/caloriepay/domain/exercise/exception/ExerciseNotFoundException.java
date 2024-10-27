package com.pknu.caloriepay.domain.exercise.exception;

import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;

public class ExerciseNotFoundException extends CustomException {

    public ExerciseNotFoundException(ResCode resCode) {
        super(resCode);
    }
}
