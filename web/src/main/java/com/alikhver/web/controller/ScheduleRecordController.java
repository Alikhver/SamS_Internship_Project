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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Positive;
import java.util.Date;

@Controller
@RequestMapping("/organisation/{orgId}")
@RequiredArgsConstructor
@Validated
public class ScheduleRecordController {
    private final OrganisationFacade organisationFacade;
    private final WorkerFacade workerFacade;

    @GetMapping("/select-worker")
    @ApiOperation("Select Worker to Assign Schedule")
    public ModelAndView viewSelectWorkerToAssignSchedule(@PathVariable @Positive Long orgId,
                                                         ModelAndView modelAndView) {

        var workers = organisationFacade.getWorkers(orgId);
        var org = organisationFacade.getOrganisation(orgId);

        modelAndView.addObject("workers", workers);
        modelAndView.addObject("org", org);

        modelAndView.setViewName("select-time/selectWorkerToAssignSchedule");

        return modelAndView;
    }

    @GetMapping("/select-worker/{workerId}/schedule")
    @ApiOperation("Assign Schedule")
    public ModelAndView viewAssignSchedule(@PathVariable @Positive Long orgId,
                                           @PathVariable @Positive Long workerId,
                                           ModelAndView modelAndView) {

        var worker = workerFacade.getWorkerById(workerId);
        var organisation = organisationFacade.getOrganisation(orgId);

        modelAndView.addObject("worker", worker);
        modelAndView.addObject("org", organisation);

        modelAndView.setViewName("select-time/assignSchedule");

        Date date = new Date();

        return modelAndView;
    }
}
