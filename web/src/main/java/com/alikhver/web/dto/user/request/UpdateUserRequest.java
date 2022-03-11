package com.alikhver.web.dto.user.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class UpdateUserRequest {
    @Size(min = 7, max = 30)
    private String login;
    @Size(min = 7, max = 30)
    private String password;
}
