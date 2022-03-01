package com.alikhver.web.dto.profile.request;

import lombok.Getter;

@Getter
public class UpdateProfileRequest {
    //TODO validate
    private String firstName;
    private String lastName;
    private String phoneNumber;
//    @Email
//    @Nullable
    private String email;
}
