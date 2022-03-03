package com.alikhver.web.dto.user.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    //TODO validate
    private String login;
    private String password;
}
