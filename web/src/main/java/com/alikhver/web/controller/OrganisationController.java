package com.alikhver.web.controller;

import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.facade.OrganisationFacade;
import com.alikhver.web.facade.UtilityFacade;
import com.alikhver.web.facade.WorkerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping("/organisation")
@RequiredArgsConstructor
public class OrganisationController {
    private final OrganisationFacade organisationFacade;
    private final WorkerFacade workerFacade;
    private final UtilityFacade utilityFacade;

    @GetMapping("/{id}")
    public ModelAndView viewOrganisation(@PathVariable @Positive Long id, ModelAndView modelAndView) {
        var org = organisationFacade.getOrganisation(id);

        modelAndView.addObject("org", org);
        modelAndView.setViewName("organisation");

        return modelAndView;
    }

    @GetMapping("/{id}/workers")
    public ModelAndView viewWorkers(@PathVariable(name = "id") @Positive Long orgId,
                                    @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                    @RequestParam(defaultValue = "5") @Positive int size,
                                    @RequestParam(name = "utility", required = false) @Positive Long utilityId,
                                    ModelAndView modelAndView) {
        Page<GetWorkerResponse> workers;

        if (utilityId == null) {
            workers = organisationFacade.getWorkers(orgId, page, size);
        } else {
            workers = utilityFacade.getWorkersOfUtility(utilityId, page, size);
        }

        modelAndView.addObject("workers", workers.getContent());
        modelAndView.addObject("orgId", orgId);
        modelAndView.setViewName("workers");

        return modelAndView;
    }

    @GetMapping("/{id}/utilities")
    public ModelAndView viewUtilities(@PathVariable(name = "id") @Positive Long orgId,
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
        modelAndView.setViewName("utilities");

        return modelAndView;
    }
}
