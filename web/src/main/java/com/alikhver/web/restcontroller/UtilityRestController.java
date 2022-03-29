package com.alikhver.web.restcontroller;

import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.request.UpdateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.facade.UtilityFacade;
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

import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utilities")
@Validated
public class UtilityRestController {
    private final UtilityFacade utilityFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get Utility")
    public ResponseEntity<GetUtilityResponse> getUtility(@PathVariable @Positive Long id) {
        GetUtilityResponse response = utilityFacade.getUtility(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping
    @ApiOperation("Create Utility")
    public ResponseEntity<CreateUtilityResponse> createUtility(@RequestBody @Validated CreateUtilityRequest request) {
        CreateUtilityResponse response = utilityFacade.createUtility(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update Utility")
    public ResponseEntity<Void> updateUtility(@PathVariable @Positive Long id, @RequestBody @Validated UpdateUtilityRequest request) {
        utilityFacade.updateUtility(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete Utility")
    public ResponseEntity<Long> deleteUtility(@PathVariable @Positive Long id) {
        utilityFacade.deleteUtility(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
