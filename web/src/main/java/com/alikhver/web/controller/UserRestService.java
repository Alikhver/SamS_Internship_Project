package com.alikhver.web.controller;

import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.DeleteUserResponse;
import com.alikhver.web.dto.user.response.GetAllUsersResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import com.alikhver.web.facade.UserFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestService {

    @Autowired
    private UserFacade userFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get User by ID")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable String id) {
        GetUserResponse user = userFacade.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/")
    @ApiOperation("Get all Users")
    public ResponseEntity<GetAllUsersResponse> getAllUsers() {
        GetAllUsersResponse users = userFacade.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PostMapping(value = "/")
    @ApiOperation("Create User")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse response = userFacade.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete User by ID")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable String id) {
        DeleteUserResponse response = userFacade.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
