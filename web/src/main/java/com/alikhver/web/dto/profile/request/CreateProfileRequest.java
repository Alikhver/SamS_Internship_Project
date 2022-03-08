package com.alikhver.web.dto.profile.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Getter
//@PropertySource("classpath:validation/validation.properties")
public class CreateProfileRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    //TODO move to properties
    @Pattern(regexp = "/\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})/")
    private String phoneNumber;
    @Email
    @NotBlank
    private String email;
    @Positive
    @NotNull
    private Long userId;
}
