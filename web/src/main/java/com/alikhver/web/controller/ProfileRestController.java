package com.alikhver.web.controller;


import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.request.UpdateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.exeption.profile.NoProfileFoundException;
import com.alikhver.web.exeption.user.NoUserFoundException;
import com.alikhver.web.facade.ProfileFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/profiles")
public class ProfileRestController {
    private final ProfileFacade profileFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get Profile")
    public ResponseEntity<GetProfileResponse> getProfile(@PathVariable Long id) throws NoProfileFoundException {
        return new ResponseEntity<>(profileFacade.getProfile(id), HttpStatus.OK);
    }

    @GetMapping("/")
    @ApiOperation("Get all Profiles")
    public ResponseEntity<List<GetProfileResponse>> getProfiles() {
        return new ResponseEntity<>(profileFacade.getProfiles(), HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation("Create Profile")
    public ResponseEntity<CreateProfileResponse> createProfile(@RequestBody @Validated CreateProfileRequest request) throws NoUserFoundException {
        return new ResponseEntity<>(profileFacade.createProfile(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update Profile")
    public void updateProfile(@PathVariable Long id, @RequestBody @Validated UpdateProfileRequest request) {
        profileFacade.updateProfile(id, request);
    }

}