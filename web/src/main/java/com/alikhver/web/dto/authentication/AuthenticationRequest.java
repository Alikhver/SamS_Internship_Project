package com.alikhver.web.dto.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthenticationRequest {
    @NotBlank
    @Size(min = 7, max = 30)
    private String login;
    @NotBlank
    @Size(min = 7, max = 30)
    private String password;
}
