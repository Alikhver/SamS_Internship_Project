package com.alikhver.web.controller.rest;

import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.request.UpdateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.facade.OrganisationFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/organisations")
@Validated
public class OrganisationRestController {
    private final OrganisationFacade organisationFacade;

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @ApiOperation("Get Organisation")
    public ResponseEntity<GetOrganisationResponse> getOrganisation(@PathVariable @Positive Long id) {
        GetOrganisationResponse response = organisationFacade.getOrganisation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation("Get Organisations")
    public ResponseEntity<Page<GetOrganisationResponse>> getOrganisations(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                                          @RequestParam(defaultValue = "5") @Positive int size) {
        Page<GetOrganisationResponse> response = organisationFacade.getOrganisations(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/workers")
    @PreAuthorize("permitAll()")
    @ApiOperation("Get Workers of Organisation")
    public ResponseEntity<Page<GetWorkerResponse>> getWorkers(@PathVariable @Positive Long id,
                                                              @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                              @RequestParam(defaultValue = "5") @Positive int size) {
        Page<GetWorkerResponse> response = organisationFacade.getWorkers(id, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/utilities")
    @PreAuthorize("permitAll()")
    @ApiOperation("Get Utilities of Organisation")
    public ResponseEntity<Page<GetUtilityResponse>> getUtilities(@PathVariable @Positive Long id,
                                                                 @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                                 @RequestParam(defaultValue = "5") @Positive int size) {
        Page<GetUtilityResponse> response = organisationFacade.getUtilities(id, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ANONYMOUS')")
    @ApiOperation("Create Organisation")
    public ResponseEntity<CreateOrganisationResponse> createOrganisation(@RequestBody @Validated CreateOrganisationRequest request) {
        CreateOrganisationResponse response = organisationFacade.createOrganisation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REDACTOR')")
    @ApiOperation("Update Organisation")
    public void updateOrganisation(@PathVariable @Positive Long id, @RequestBody @Validated UpdateOrganisationRequest request) {
        organisationFacade.updateOrganisation(id, request);
    }

    @PatchMapping("/{id}/suspend")
    @PreAuthorize("hasAnyAuthority('REDACTOR', 'ADMIN')")
    @ApiOperation("Suspend working process of Organisation")
    public ResponseEntity<Void> suspendOrganisation(@PathVariable @Positive Long id) {
        organisationFacade.suspendOrganisation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{id}/launch")
    @PreAuthorize("hasAnyAuthority('REDACTOR', 'ADMIN')")
    @ApiOperation("Launch working process of Organisation")
    public ResponseEntity<Void> launchOrganisation(@PathVariable @Positive Long id) {
        organisationFacade.launchOrganisation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('REDACTOR', 'ADMIN')")
    @ApiOperation("Delete Organisation")
    public ResponseEntity<Long> deleteOrganisation(@PathVariable @Positive Long id) {
        organisationFacade.deleteOrganisation(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
