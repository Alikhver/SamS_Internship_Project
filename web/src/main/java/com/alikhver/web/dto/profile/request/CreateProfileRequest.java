package com.alikhver.web.dto.profile.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
public class CreateProfileRequest {
    @NotBlank
    @Size(max = 45)
    private String firstName;
    @NotBlank
    @Size(max = 45)
    private String lastName;
    @NotBlank
    //TODO move to properties
    @Pattern(regexp = "^\\s*\\+?375((33\\d{7})|(29\\d{7})|(44\\d{7}|)|(25\\d{7}))\\s*$",
            message = "Not correct phone number")
    private String phoneNumber;
    @Email
    @Size(max = 45)
    @NotBlank
    private String email;
    @Positive
    @NotNull
    private Long userId;
}
