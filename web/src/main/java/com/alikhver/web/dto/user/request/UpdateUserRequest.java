package com.alikhver.web.dto.user.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String login;
    private String password;
}
