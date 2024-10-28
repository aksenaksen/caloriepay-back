package com.pknu.caloriepay.domain.calender.exception;

import com.pknu.caloriepay.global.enums.ResCode;
import com.pknu.caloriepay.global.error.CustomException;
import lombok.Getter;

@Getter
public class StartIsAfterEndDateException extends CustomException {
    public StartIsAfterEndDateException(ResCode resCode) {
        super(resCode);
    }
}
