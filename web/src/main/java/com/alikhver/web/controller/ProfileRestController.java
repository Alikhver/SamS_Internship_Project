package com.alikhver.web.controller;


import com.alikhver.web.facade.ProfileFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileRestController {
    private final ProfileFacade profileFacade;


}
