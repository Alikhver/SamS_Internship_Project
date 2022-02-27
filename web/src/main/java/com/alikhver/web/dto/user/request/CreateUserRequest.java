package com.alikhver.web.dto.user.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateUserRequest {
    @NotBlank
    private String login;

    @NotBlank
    private String password;

    private String role;
}
