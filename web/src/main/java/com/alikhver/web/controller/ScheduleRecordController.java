package com.alikhver.web.controller;

import com.alikhver.web.facade.OrganisationFacade;
import com.alikhver.web.facade.WorkerFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/organisation/{orgId}")
@RequiredArgsConstructor
@Validated
public class ScheduleRecordController {
    private final OrganisationFacade organisationFacade;
    private final WorkerFacade workerFacade;

    @GetMapping("/select-worker-to-assign-schedule")
    @ApiOperation("Select Worker to Assign Schedule")
    public ModelAndView viewSelectWorkerToAssignSchedule(@PathVariable @Positive Long orgId,
                                                         @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                         @RequestParam(defaultValue = "12") @Positive int size,
                                                         ModelAndView modelAndView) {
        var workers = organisationFacade.getWorkers(orgId, page, size);
        var org = organisationFacade.getOrganisation(orgId);

        modelAndView.addObject("workers", workers);
        modelAndView.addObject(org);

        modelAndView.setViewName("");

        return modelAndView;
    }
}
