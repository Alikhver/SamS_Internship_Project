package com.alikhver.web.controller;

import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import com.alikhver.web.facade.OrganisationFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organisations")
public class OrganisationRestController {
    private final OrganisationFacade organisationFacade;

    //TODO implement
    @GetMapping("/{id}")
    @ApiOperation("Get Organisation")
    public ResponseEntity<GetOrganisationResponse> getOrganisation(@PathVariable Long id) throws NoOrganisationFoundException {
        GetOrganisationResponse response = organisationFacade.getOrganisation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation("Create Organisation")
    public ResponseEntity<CreateOrganisationResponse> createOrganisation(@RequestBody @Validated CreateOrganisationRequest request) {
        CreateOrganisationResponse response = organisationFacade.createOrganisation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}