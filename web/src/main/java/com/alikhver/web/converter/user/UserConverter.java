package com.alikhver.web.converter.user;

import com.alikhver.model.entity.User;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserConverter {
    private final UserToGetUserResponseConverter userToGetUserResponseConverter;
    private final PageOfUsersToPageOfGetUserResponseConverter pageOfUsersToPageOfGetUserResponseConverter;
    private final CreateUserRequestToUserConverter createUserRequestToUserConverter;
    private final UserToCreateUserResponseConverter userToCreateUserResponseConverter;

    public GetUserResponse mapToGetUserResponse(User user) {
        log.info("mapToGetUserResponse -> start");

        var response = userToGetUserResponseConverter.convert(user);

        log.info("mapToGetUserResponse -> done");
        return response;
    }

    public Page<GetUserResponse> mapToListOfGetUserResponse(Page<User> users) {
        log.info("mapToListOfGetUserResponse -> start");

        var response = pageOfUsersToPageOfGetUserResponseConverter.convert(users);

        log.info("mapToListOfGetUserResponse -> done");
        return response;
    }

    public User mapToUser(CreateUserRequest request) {
        log.info("mapToUser -> start");

        var response = createUserRequestToUserConverter.convert(request);

        log.info("mapToUser -> done");
        return response;
    }

    public CreateUserResponse mapToCreateUserResponse(User user) {
        log.info("mapToCreateUserResponse -> start");

        var response = userToCreateUserResponseConverter.convert(user);

        log.info("mapToCreateUserResponse -> done");
        return response;
    }
}
