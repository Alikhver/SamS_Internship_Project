package com.alikhver.web.controller;

import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.request.UpdateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import com.alikhver.web.exeption.user.NoUserFoundException;
import com.alikhver.web.exeption.user.UserAlreadyExistsException;
import com.alikhver.web.facade.UserFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestController {
    private final UserFacade userFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get User by ID")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long id) throws NoUserFoundException {
        GetUserResponse response = userFacade.getUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    @ApiOperation("Get all Users")
    public ResponseEntity<List<GetUserResponse>> getAllUsers() {
        List<GetUserResponse> response = userFacade.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/")
    @ApiOperation("Create User")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Validated CreateUserRequest request) throws UserAlreadyExistsException {
        CreateUserResponse response = userFacade.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update User")
    public void updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        userFacade.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete User by ID")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) throws NoUserFoundException {
        userFacade.deleteUser(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
