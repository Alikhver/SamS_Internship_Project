package com.alikhver.web.controller;


import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.facade.OrganisationFacade;
import com.alikhver.web.facade.UtilityFacade;
import com.alikhver.web.facade.WorkerFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping("/organisation/{orgId}/utilities")
@RequiredArgsConstructor
@Validated
public class UtilityController {
    private final OrganisationFacade organisationFacade;
    private final WorkerFacade workerFacade;
    private final UtilityFacade utilityFacade;

    @GetMapping()
    @ApiOperation("View Utilities")
    public ModelAndView viewUtilities(@PathVariable @Positive Long orgId,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                      @RequestParam(defaultValue = "5") @Positive int size,
                                      @RequestParam(name = "worker", required = false) @Positive Long workerId,
                                      ModelAndView modelAndView) {
        Page<GetUtilityResponse> utilities;

        if (workerId == null) {
            utilities = organisationFacade.getUtilities(orgId, page, size);
        } else {
            utilities = workerFacade.getUtilitiesOfWorker(workerId, page, size);
        }

        modelAndView.addObject("utilities", utilities.getContent());
        modelAndView.addObject("orgId", orgId);
        modelAndView.setViewName("utility/utilities");

        return modelAndView;
    }

    @GetMapping("/{utilityId}/update")
    @ApiOperation("Update Utility")
    public ModelAndView viewUpdateUtility(@PathVariable @Positive Long orgId,
                                          @PathVariable @Positive Long utilityId,
                                          ModelAndView modelAndView) {

        modelAndView.setViewName("utility/updateUtility");


        return modelAndView;
    }

    @GetMapping("/create")
    @ApiOperation("Create Utility")
    public ModelAndView viewUpdateUtility(@PathVariable @Positive Long orgId,
                                          ModelAndView modelAndView) {

        modelAndView.setViewName("utility/createUtility");


        return modelAndView;
    }
}
