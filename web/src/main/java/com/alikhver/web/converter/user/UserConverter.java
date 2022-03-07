package com.alikhver.web.converter.user;

import com.alikhver.model.entity.User;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final UserToGetUserResponseConverter userToGetUserResponseConverter;
    private final PageOfUsersToPageOfGetUserResponseConverter pageOfUsersToPageOfGetUserResponseConverter;
    private final CreateUserRequestToUserConverter createUserRequestToUserConverter;
    private final UserToCreateUserResponseConverter userToCreateUserResponseConverter;

    public GetUserResponse mapToGetUserResponse(User user) {
        return userToGetUserResponseConverter.convert(user);
    }

    public Page<GetUserResponse> mapToListOfGetUserResponse(Page<User> users) {
        return pageOfUsersToPageOfGetUserResponseConverter.convert(users);
    }

    public User mapToUser(CreateUserRequest request) {
        return createUserRequestToUserConverter.convert(request);
    }

    public CreateUserResponse mapToCreateUserResponse(User user) {
        return userToCreateUserResponseConverter.convert(user);
    }
}
