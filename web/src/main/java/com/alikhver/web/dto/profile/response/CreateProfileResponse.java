package com.alikhver.web.dto.profile.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProfileResponse {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final Long userId;
}
