package com.alikhver.web.controller;


import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.exception.CustomLocalizedException;
import com.alikhver.web.facade.OrganisationFacade;
import com.alikhver.web.facade.UtilityFacade;
import com.alikhver.web.facade.WorkerFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Locale;
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
    @PreAuthorize("permitAll()")
    @ApiOperation("View Utilities")
    public ModelAndView viewUtilities(@PathVariable @Positive Long orgId,
                                      @RequestParam(name = "worker", required = false) @Positive Long workerId,
                                      ModelAndView modelAndView) {
        List<GetUtilityResponse> utilities;

        if (workerId == null) {
            utilities = organisationFacade.getUtilities(orgId);
        } else {
            utilities = workerFacade.getUtilitiesOfWorker(workerId).stream().sorted((GetUtilityResponse a, GetUtilityResponse b) -> {
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
        modelAndView.addObject("utilities", utilities);
        modelAndView.addObject("orgId", orgId);
        modelAndView.setViewName("utility/utilities");

        return modelAndView;
    }

    @GetMapping("/{utilityId}/update")
    @PreAuthorize("hasAuthority('REDACTOR')")
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
    @PreAuthorize("hasAuthority('REDACTOR')")
    @ApiOperation("Create Utility")
    public ModelAndView viewUpdateUtility(@PathVariable @Positive Long orgId,
                                          ModelAndView modelAndView) {
        var organisation = organisationFacade.getOrganisation(orgId);

        modelAndView.addObject("orgName", organisation.getName());
        modelAndView.setViewName("utility/createUtility");

        return modelAndView;
    }

    @ExceptionHandler(CustomLocalizedException.class)
    public ModelAndView handleNoOrganisationFoundException(CustomLocalizedException e) {
        ModelAndView modelAndView = new ModelAndView("error/customError");


        Locale locale = LocaleContextHolder.getLocale();

        modelAndView.addObject("msg", e.getLocalizedMessage(locale));
        modelAndView.addObject("status", e.status);

        return modelAndView;
    }
}
