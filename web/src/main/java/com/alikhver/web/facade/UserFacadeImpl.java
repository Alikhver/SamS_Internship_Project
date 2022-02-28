package com.alikhver.web.facade;

import com.alikhver.model.entity.User;
import com.alikhver.model.service.UserService;
import com.alikhver.web.converter.UserConverter;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.request.UpdateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetAllUsersResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import com.alikhver.web.exeption.user.UserAlreadyExistsException;
import com.alikhver.web.exeption.user.NoUserFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    private final UserConverter userConverter;

    public GetUserResponse getUser(Long id) throws NoUserFoundException {
        if (userService.userExistsById(id)) {
            User user = userService.getUser(id);
            return userConverter.convertUserToGetUserResponse(user);
        } else {
            throw new NoUserFoundException(
                    "User with id = " + id + " does not exist"
            );
        }
    }

    public GetAllUsersResponse getAllUsers() {
        List<User> users = userService.getAllUsers();
        return userConverter.convertUsersToGetAllUsersResponse(users);
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws UserAlreadyExistsException {
        // TODO validate

        if (userService.userExistsByLogin(request.getLogin())) {
            throw new UserAlreadyExistsException(
                    "User with login=" + request.getLogin() + " already exists"
            );
        } else {
            User user = userConverter.convertCreateUserResponseToUser(request);
            user = userService.createUser(user);
            return userConverter.convertUserToCreateUserResponse(user);
        }
    }


    public void deleteUser(Long id) throws NoUserFoundException {
        // TODO implement if user does not exist
        if (userService.userExistsById(id)) {
            userService.deleteUser(id);
        } else {
            throw new NoUserFoundException(
                    "User with id = " + id + " does not exist"
            );
        }
    }

    public void updateUser(Long id, UpdateUserRequest request) {
        User user = userConverter.convertUpdateUserRequestToUser(request);
        Optional.ofNullable(request.getLogin()).ifPresent(user::setLogin);
        Optional.ofNullable(request.getPassword()).ifPresent(user::setPassword);
        // TODO UserRole
        userService.updateUser(id, user);
    }
}
