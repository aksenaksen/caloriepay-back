package com.pknu.caloriepay.global.error;

import com.pknu.caloriepay.global.enums.ResCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    final ResCode errorCode;

    public CustomException(ResCode resCode) {
        super(resCode.getMessage());
        this.errorCode = resCode;
    }
}
