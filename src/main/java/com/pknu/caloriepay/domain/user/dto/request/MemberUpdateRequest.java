package com.pknu.caloriepay.domain.user.dto.request;

import com.pknu.caloriepay.domain.user.domain.ActivityLevel;
import com.pknu.caloriepay.domain.user.domain.Gender;
import com.pknu.caloriepay.domain.user.domain.Goal;
import lombok.Data;

@Data
public class MemberUpdateRequest {
    private String email;
    private String nickname;
    private String phoneNumber;
    private Integer age;
    private Double height;
    private Double weight;
    private Double targetWeight;
    private Gender gender;
    private Goal goal;
    private ActivityLevel activityLevel;
    private Boolean visible;
}
