package com.alikhver.web.facade;

import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.request.UpdateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;

import java.util.List;

public interface UserFacade {
    GetUserResponse getUser(Long id);

    List<GetUserResponse> getAllUsers();

    CreateUserResponse createUser(CreateUserRequest request);

    void deleteUser(Long id);

    void updateUser(Long id, UpdateUserRequest request);
}
