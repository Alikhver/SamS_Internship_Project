package com.alikhver.web.dto.profile.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class UpdateProfileRequest {
    @Size(max = 45)
    private String firstName;
    @Size(max = 45)
    private String lastName;
    //TODO move to properties
    @Pattern(regexp = "^\\s*\\+?375((33\\d{7})|(29\\d{7})|(44\\d{7}|)|(25\\d{7}))\\s*$",
            message = "Not correct phone number")
    private String phoneNumber;
    @Email
    @Size(max = 45)
    private String email;
    @Size(min = 7, max = 30)
    private String password;
}
