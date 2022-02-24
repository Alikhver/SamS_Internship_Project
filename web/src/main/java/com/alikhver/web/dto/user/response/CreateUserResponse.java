package com.alikhver.web.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUserResponse {
    private String login;
    private String password;
    private String role;
}
