package com.alikhver.web.facade;

import com.alikhver.model.entity.User;
import com.alikhver.model.service.UserService;
import com.alikhver.web.converter.UserConverter;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.request.UpdateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetAllUsersResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import com.alikhver.web.exeption.user.NoUserFoundException;
import com.alikhver.web.exeption.user.UserAlreadyExistsException;
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
        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return userConverter.mapToGetUserResponse(user);
        } else {
            throw new NoUserFoundException(
                    "User with id = " + id + " does not exist"
            );
        }
    }

    public GetAllUsersResponse getAllUsers() {
        List<User> users = userService.getAllUsers();
        return userConverter.mapToGetAllUsersResponse(users);
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws UserAlreadyExistsException {
        // TODO validate

        if (userService.userExistsByLogin(request.getLogin())) {
            throw new UserAlreadyExistsException(
                    "User with login=" + request.getLogin() + " already exists"
            );
        } else {
            User user = userConverter.mapToUser(request);
            user = userService.createUser(user);
            return userConverter.mapToCreateUserResponse(user);
        }
    }

    @Transactional
    public void deleteUser(Long id) throws NoUserFoundException {
        // TODO implement if user does not exist
        if (userService.userExistsById(id)) {
            userService.deleteUser(id);
        } else {
            throw new NoUserFoundException(
                    "No User with id = " + id + " found"
            );
        }
    }

    @Transactional
    public void updateUser(Long id, UpdateUserRequest request) {
        Optional<User> optionalUser = userService.getUser(id);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new NoUserFoundException(
                    "No user with id = " + id + " found"
            );
        }
        Optional.ofNullable(request.getLogin()).ifPresent(user::setLogin);
        Optional.ofNullable(request.getPassword()).ifPresent(user::setPassword);
        //TODO user role
        userService.updateUser(user);
    }
}
