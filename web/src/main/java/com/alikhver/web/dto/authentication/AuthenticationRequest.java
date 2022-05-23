package com.alikhver.web.dto.authentication;

import lombok.Getter;

@Getter
public class AuthenticationRequest {
    private String login;
    private String password;
}
