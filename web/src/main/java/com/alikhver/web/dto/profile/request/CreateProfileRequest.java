package com.alikhver.web.dto.profile.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class CreateProfileRequest {
    //TODO validate
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String phoneNumber;
    @Email
    private String email;
    @Positive
    private Long userId;
}
