package com.alikhver.web.controller;

import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.request.UpdateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import com.alikhver.web.exception.user.NoUserFoundException;
import com.alikhver.web.facade.UserFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserRestController {
    private final UserFacade userFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get User by Id")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable @Positive Long id) throws NoUserFoundException {
        GetUserResponse response = userFacade.getUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get Users")
    public ResponseEntity<Page<GetUserResponse>> getAllUsers(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                             @RequestParam(defaultValue = "5") @Positive int size) {
        Page<GetUserResponse> response = userFacade.getAllUsers(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping
    @ApiOperation("Create User")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Validated CreateUserRequest request) {
        CreateUserResponse response = userFacade.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update User")
    public void updateUser(@PathVariable @Positive Long id,
                           @RequestBody @Validated UpdateUserRequest request) {
        userFacade.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete User by Id")
    public ResponseEntity<Long> deleteUser(@PathVariable @Positive Long id) {
        userFacade.deleteUser(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
