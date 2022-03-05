package com.alikhver.web.controller;

import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.request.UpdateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import com.alikhver.web.facade.OrganisationFacade;
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
@RequestMapping("/organisations")
public class OrganisationRestController {
    private final OrganisationFacade organisationFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get Organisation")
    public ResponseEntity<GetOrganisationResponse> getOrganisation(@PathVariable Long id) throws NoOrganisationFoundException {
        GetOrganisationResponse response = organisationFacade.getOrganisation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    @ApiOperation("Get All Organisation")
    public ResponseEntity<List<GetOrganisationResponse>> getOrganisations() {
        List<GetOrganisationResponse> response = organisationFacade.getOrganisations();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation("Create Organisation")
    public ResponseEntity<CreateOrganisationResponse> createOrganisation(@RequestBody @Validated CreateOrganisationRequest request) {
        CreateOrganisationResponse response = organisationFacade.createOrganisation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update Organisation")
    public void updateOrganisation(@PathVariable Long id, @RequestBody @Validated UpdateOrganisationRequest request) {
        organisationFacade.updateOrganisation(id, request);
    }

    @GetMapping("/{id}/suspend")
    @ApiOperation("Suspend working process of Organisation")
    public ResponseEntity<Void> suspendOrganisation(@PathVariable Long id) {
        organisationFacade.suspendOrganisation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{id}/launch")
    @ApiOperation("Launch working process of Organisation")
    public ResponseEntity<Void> launchOrganisation(@PathVariable Long id) {
        organisationFacade.launchOrganisation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete Organisation")
    public ResponseEntity<Long> deleteOrganisation(@PathVariable Long id) {
        organisationFacade.deleteOrganisation(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
