package com.alikhver.web.controller;

import com.alikhver.web.facade.OrganisationFacade;
import com.alikhver.web.facade.UtilityFacade;
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

@Controller
@RequestMapping("/organisation")
@RequiredArgsConstructor
@Validated
public class OrganisationController {
    private final OrganisationFacade organisationFacade;
    private final WorkerFacade workerFacade;
    private final UtilityFacade utilityFacade;

    @GetMapping("/{orgId}")
    @ApiOperation("View Organisation")
    public ModelAndView viewOrganisation(@PathVariable @Positive Long orgId,
                                         @RequestParam(value = "worker", required = false) @Positive Long workerId,
                                         @RequestParam(value = "utility", required = false) @Positive Long utilityId,
                                         @RequestParam(value = "record", required = false) @Positive Long recordId,
                                         ModelAndView modelAndView) {
        var org = organisationFacade.getOrganisation(orgId);
        modelAndView.addObject("org", org);

        if (workerId != null) {
            var worker = workerFacade.getWorkerById(workerId);
            modelAndView.addObject("worker", worker);
        }

        if (utilityId != null) {
            var utility = utilityFacade.getUtility(utilityId);
            modelAndView.addObject("utility", utility);
        }

//        if (recordId != null) {
//            var record = recordService.getRecord(recordId);
//            modelAndView.addObject("record", record);
//        }

        modelAndView.setViewName("organisation/organisation");

        return modelAndView;
    }


    @GetMapping("/{orgId}/update")
    @ApiOperation("Update Organisation")
    public ModelAndView viewUpdateOrganisation(@PathVariable @Positive Long orgId,
                                               ModelAndView modelAndView) {

        var organisation = organisationFacade.getOrganisation(orgId);
        modelAndView.addObject("org", organisation);
        modelAndView.setViewName("organisation/updateOrganisation");

        return modelAndView;
    }
    @GetMapping("/{orgId}/manage")
    @ApiOperation("Manage Organisation by Admin")
    public ModelAndView viewManageOrganisation(@PathVariable @Positive Long orgId,
                                               ModelAndView modelAndView) {

        var organisation = organisationFacade.getOrganisation(orgId);
        modelAndView.addObject("org", organisation);
        modelAndView.setViewName("organisation/manageOrganisation");

        return modelAndView;
    }
}
