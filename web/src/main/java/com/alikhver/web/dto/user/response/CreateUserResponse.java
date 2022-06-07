package com.alikhver.web.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUserResponse {
    private final long id;
    private final String login;
    private final String role;
}
