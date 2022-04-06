package com.alikhver.web.restcontroller;


import com.alikhver.web.dto.profile.request.CreateProfileRequest;
import com.alikhver.web.dto.profile.request.UpdateProfileRequest;
import com.alikhver.web.dto.profile.response.CreateProfileResponse;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.exception.profile.NoProfileFoundException;
import com.alikhver.web.exception.user.NoUserFoundException;
import com.alikhver.web.facade.ProfileFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/profiles")
@Validated
public class ProfileRestController {
    private final ProfileFacade profileFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get Profile")
    public ResponseEntity<GetProfileResponse> getProfile(@PathVariable @Positive Long id) throws NoProfileFoundException {
        return new ResponseEntity<>(profileFacade.getProfile(id), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get Profiles")
    public ResponseEntity<Page<GetProfileResponse>> getProfiles(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                                @RequestParam(defaultValue = "5") @Positive int size) {
        Page<GetProfileResponse> response = profileFacade.getProfiles(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create Profile")
    public ResponseEntity<CreateProfileResponse> createProfile(@RequestBody @Validated CreateProfileRequest request) throws NoUserFoundException {
        return new ResponseEntity<>(profileFacade.createProfile(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update Profile")
    public void updateProfile(@PathVariable @Positive Long id, @RequestBody @Validated UpdateProfileRequest request) {
        profileFacade.updateProfile(id, request);
    }

    //TODO implement delete profile

}
