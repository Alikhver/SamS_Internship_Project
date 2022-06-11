package com.alikhver.web.facade;

import com.alikhver.model.entity.User;
import com.alikhver.model.service.UserService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.user.UserConverter;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.request.UpdateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import com.alikhver.web.exception.user.NoUserFoundException;
import com.alikhver.web.exception.user.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final UserConverter userConverter;
    private final ValidationHelper validationHelper;
    private final @Lazy PasswordEncoder passwordEncoder;

    @Override
    public GetUserResponse getUser(Long id) {
        log.info("getUser -> start");

        validationHelper.validateForCorrectId(id, "UserId");

        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            var response = userConverter.mapToGetUserResponse(user);

            log.info("getUser -> done");
            return response;
        } else {
            log.warn("NoUserFoundException is thrown");
            throw new NoUserFoundException(id);
        }
    }

    @Override
    public Page<GetUserResponse> getAllUsers(int page, int size) {
        log.info("getAllUsers -> start");

        validatePageAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getUsers(pageable);

        var response = userConverter.mapToListOfGetUserResponse(users);

        log.info("getAllUsers -> done");
        return response;
    }

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        log.info("createUser -> start");

        if (userService.existsUserByLogin(request.getLogin())) {
            log.warn("UserAlreadyExistsException is thrown");
            throw new UserAlreadyExistsException(request.getLogin());
        } else {
            User user = userConverter.mapToUser(request);
            user.setPassword(encodePassword(user.getPassword()));
            user = userService.saveUser(user);

            var response = userConverter.mapToCreateUserResponse(user);

            log.info("createUser -> done");
            return response;
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        log.info("deleteUser -> start");

        if (!userService.existsUserById(id)) {
            log.warn("NoUserFoundException is thrown");
            throw new NoUserFoundException(id);
        } else {
            userService.deleteUser(id);
        }

        log.info("profileFacade::deleteUser -> done");
    }

    @Transactional
    @Override
    public void updateUser(Long id, UpdateUserRequest request) {
        log.info("updateUser -> start");

        Optional<User> optionalUser = userService.getUser(id);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            log.warn("NoUserFoundException is thrown");
            throw new NoUserFoundException(id);
        }
        Optional.ofNullable(request.getPassword()).map(this::encodePassword).ifPresent(user::setPassword);

        log.info("updateUser -> done");
        userService.saveUser(user);
    }

    @Override
    public GetUserResponse findByLogin(String login) {
        log.info("findByLogin -> start");

        validationHelper.validateForCorrectString(login, "Login");

        User user = userService.findUserByLogin(login).orElseThrow(() -> {
            log.warn("NoUserFoundException is thrown");
            throw new NoUserFoundException(login);
        });

        var response = userConverter.mapToGetUserResponse(user);

        log.info("findByLogin -> done");
        return response;
    }

    @Override
    public boolean loginExists(String login) {
        log.info("loginExists -> start");

        boolean exists = userService.existsUserByLogin(login);

        log.info("loginExists -> done");
        return exists;
    }

    private void validatePageAndSize(int page, int size) {
        log.info("validatePageAndSize -> start");

        if (page < 0) {
            log.warn("IllegalArgumentException is thrown");
            throw new IllegalArgumentException(
                    "Page should be positive or zero"
            );
        }

        if (size <= 0) {
            log.warn("IllegalArgumentException is thrown");
            throw new IllegalArgumentException(
                    "Size must be positive"
            );
        }

        log.info("validateForPageAndSize -> done");
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
