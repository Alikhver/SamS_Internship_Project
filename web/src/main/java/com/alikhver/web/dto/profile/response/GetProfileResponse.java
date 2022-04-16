package com.alikhver.web.dto.profile.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Long userId;
}
