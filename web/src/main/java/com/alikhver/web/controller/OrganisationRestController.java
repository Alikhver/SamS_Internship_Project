package com.alikhver.web.controller;

import com.alikhver.web.facade.OrganisationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organisations")
public class OrganisationRestController {
    private final OrganisationFacade organisationFacade;
}
