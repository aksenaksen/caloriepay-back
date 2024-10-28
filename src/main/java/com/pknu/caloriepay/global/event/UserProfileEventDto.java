package com.pknu.caloriepay.global.event;

import com.pknu.caloriepay.domain.user.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileEventDto {
    private Long userId;

    private Profile profile;
}
