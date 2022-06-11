package com.alikhver.web.dto.user.request;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class CreateUserRequest {
    @NotBlank
    @Size(min = 7, max = 30)
    private String login;
    @NotBlank
    @Size(min = 7, max = 30)
    private String password;
}
