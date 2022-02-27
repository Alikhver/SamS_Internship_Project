package com.alikhver.web.dto.profile.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProfileResponse {
    private String firstName;
    private String lastName;
    private String email;
}
