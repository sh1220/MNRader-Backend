package com.example.mnraderbackend.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmRequest {

    @NotNull(message = "enabled: {NotNull}")
    private Boolean enabled;

    private String fcmToken;

    @AssertTrue(message = "알람을 활성화할 경우 alarmToken이 필요합니다.")
    private boolean isFcmTokenValid() {
        return enabled == null || !enabled || (fcmToken != null && !fcmToken.trim().isEmpty());
    }

}
