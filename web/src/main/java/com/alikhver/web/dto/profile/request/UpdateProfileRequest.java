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
    @Pattern(regexp = "^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$",
            message = "Not correct phone number")
    private String phoneNumber;
    @Email
    @Size(max = 45)
    private String email;
}
