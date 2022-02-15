package com.alikhver.web.controller;

import com.alikhver.model.entity.User;
import com.alikhver.web.facade.UserFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestService {

    @Autowired
    private UserFacade userFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get User by ID")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = userFacade.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PostMapping(value = "/")
//    @ApiOperation("Create User")
//    public ResponseEntity<String> createUser(@RequestBody User user) {
//
//        return new ResponseEntity<>(employee.getId(), HttpStatus.CREATED);
//    }
}
