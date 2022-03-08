package com.alikhver.web.dto.profile.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    //TODO move to properties
    @Pattern(regexp = "/\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})/")
    private String phoneNumber;
    @Email
    private String email;
}
