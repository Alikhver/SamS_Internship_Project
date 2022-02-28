package com.alikhver.web.facade;

import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.request.UpdateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetAllUsersResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;

public interface UserFacade {
    GetUserResponse getUser(Long id);

    GetAllUsersResponse getAllUsers();

    CreateUserResponse createUser(CreateUserRequest request);

    void deleteUser(Long id);

    void updateUser(Long id, UpdateUserRequest request);
}
