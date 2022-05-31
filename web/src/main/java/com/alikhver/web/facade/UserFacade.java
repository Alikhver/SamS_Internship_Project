package com.alikhver.web.facade;

import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.request.UpdateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import org.springframework.data.domain.Page;

public interface UserFacade {
    GetUserResponse getUser(Long id);

    Page<GetUserResponse> getAllUsers(int page, int size);

    CreateUserResponse createUser(CreateUserRequest request);

    void deleteUser(Long id);

    void updateUser(Long id, UpdateUserRequest request);

    GetUserResponse findByLogin(String login);

    boolean loginExists(String login);
}
