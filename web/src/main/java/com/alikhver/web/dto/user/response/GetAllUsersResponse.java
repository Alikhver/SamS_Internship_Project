package com.alikhver.web.dto.user.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllUsersResponse {
    private List<GetUserResponse> users;
}
