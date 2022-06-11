package com.alikhver.web.dto.user.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
@Builder
public class UpdateUserRequest {
    @Size(min = 7, max = 30)
    private String password;
}
