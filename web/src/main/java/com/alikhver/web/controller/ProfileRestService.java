package com.alikhver.web.controller;


import com.alikhver.web.facade.ProfileFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class ProfileRestService {

    private ProfileFacade profileFacade;


}
