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
import java.util.List;
import java.util.stream.Collectors;

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
        Page<GetUtilityResponse> utilitiesPage;
        List<GetUtilityResponse> utilitiesList;

        if (workerId == null) {
            utilitiesPage = organisationFacade.getUtilities(orgId, page, size);
            utilitiesList = utilitiesPage.getContent();
        } else {
            utilitiesPage = workerFacade.getUtilitiesOfWorker(workerId, page, size);
            utilitiesList = utilitiesPage.stream().sorted((GetUtilityResponse a, GetUtilityResponse b) -> {
                if (a.isHasWorkers() == b.isHasWorkers() && a.isHasWorkers()) {
                    return 1;
                } else if (a.isHasWorkers() && !b.isHasWorkers()) {
                    return 1;
                } else {
                    return -1;
                }
            }).collect(Collectors.toList());
        }
        var organisation = organisationFacade.getOrganisation(orgId);

        modelAndView.addObject("orgName", organisation.getName());
        modelAndView.addObject("utilities", utilitiesList);
        modelAndView.addObject("orgId", orgId);
        modelAndView.setViewName("utility/utilities");

        return modelAndView;
    }

    @GetMapping("/{utilityId}/update")
    @ApiOperation("Update Utility")
    public ModelAndView viewUpdateUtility(@PathVariable @Positive Long orgId,
                                          @PathVariable @Positive Long utilityId,
                                          ModelAndView modelAndView) {

        var utility = utilityFacade.getUtility(utilityId);
        var organisation = organisationFacade.getOrganisation(orgId);

        modelAndView.addObject("orgName", organisation.getName());
        modelAndView.addObject("utility", utility);
        modelAndView.setViewName("utility/updateUtility");

        return modelAndView;
    }

    @GetMapping("/create")
    @ApiOperation("Create Utility")
    public ModelAndView viewUpdateUtility(@PathVariable @Positive Long orgId,
                                          ModelAndView modelAndView) {
        var organisation = organisationFacade.getOrganisation(orgId);

        modelAndView.addObject("orgName", organisation.getName());
        modelAndView.setViewName("utility/createUtility");

        return modelAndView;
    }
}
