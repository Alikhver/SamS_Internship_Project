package com.alikhver.web.dto.user.request;


import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String login;
    private String password;
    private String role;
}
