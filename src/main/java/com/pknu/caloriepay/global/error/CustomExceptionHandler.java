package com.pknu.caloriepay.global.error;

import com.pknu.caloriepay.domain.calender.exception.StartIsAfterEndDateException;
import com.pknu.caloriepay.global.dto.BaseRes;
import com.pknu.caloriepay.global.enums.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(StartIsAfterEndDateException.class)
    protected ResponseEntity<BaseRes<String>> handleCustomException(StartIsAfterEndDateException e) {
        log.error(e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(BaseRes.fail(e.getErrorCode()));
    }
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<BaseRes<String>> handleCustomException(CustomException e) {
        log.error(e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(BaseRes.fail(e.getErrorCode()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseRes<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        String errMsg=e.getBindingResult()
                .getFieldErrors().get(0).getDefaultMessage();
        log.error(errMsg);

        if (Objects.requireNonNull(errMsg).equals(ResCode.NOT_MATCHED_TITLE_FORMAT.getMessage())){
            return ResponseEntity.status(ResCode.NOT_MATCHED_TITLE_FORMAT.getHttpStatus())
                    .body(BaseRes.fail(ResCode.NOT_MATCHED_TITLE_FORMAT));
        }
        if (Objects.requireNonNull(errMsg).equals(ResCode.NOT_MATCHED_EXERCISE_TIME_FORMAT.getMessage())){
            return ResponseEntity.status(ResCode.NOT_MATCHED_TITLE_FORMAT.getHttpStatus())
                    .body(BaseRes.fail(ResCode.NOT_MATCHED_TITLE_FORMAT));
        }
        if (Objects.requireNonNull(errMsg).equals(ResCode.NOT_MATCHED_EXERCISE_TYPE_FORMAT.getMessage())){
            return ResponseEntity.status(ResCode.NOT_MATCHED_TITLE_FORMAT.getHttpStatus())
                    .body(BaseRes.fail(ResCode.NOT_MATCHED_TITLE_FORMAT));
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseRes.fail(ResCode.BAD_REQUEST));
        }
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseRes<String>> handleException(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseRes.fail(ResCode.INTERNAL_SERVER_ERROR));
    }
}