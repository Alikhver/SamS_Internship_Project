package com.alikhver.web.controller;


import com.alikhver.web.dto.worker.response.GetWorkerResponse;
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
@RequestMapping("/organisation/{orgId}/workers")
@RequiredArgsConstructor
@Validated
public class WorkerController {
    private final OrganisationFacade organisationFacade;
    private final WorkerFacade workerFacade;
    private final UtilityFacade utilityFacade;

    @GetMapping
    @ApiOperation("View Workers")
    public ModelAndView viewWorkers(@PathVariable @Positive Long orgId,
                                    @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                    @RequestParam(defaultValue = "5") @Positive int size,
                                    @RequestParam(name = "utility", required = false) @Positive Long utilityId,
                                    @RequestParam(name = "worker", required = false) @Positive Long workerId,
                                    ModelAndView modelAndView) {
        Page<GetWorkerResponse> workers;

        if (utilityId == null || workerId != null) {
            workers = organisationFacade.getWorkers(orgId, page, size);
        } else {
            workers = utilityFacade.getWorkersOfUtility(utilityId, page, size);
        }

        modelAndView.addObject("workers", workers.getContent());
        modelAndView.addObject("orgId", orgId);
        modelAndView.setViewName("worker/workers");

        return modelAndView;
    }

    @GetMapping("/create")
    @ApiOperation("Create Worker")
    public ModelAndView viewCreateWorker(@PathVariable @Positive Long orgId,
                                         ModelAndView modelAndView) {

        modelAndView.setViewName("worker/createWorker");

        return modelAndView;
    }



    @GetMapping("/{workerId}/update")
    @ApiOperation("Update Worker")
    public ModelAndView viewCreateWorker(@PathVariable @Positive Long orgId,
                                         @PathVariable @Positive Long workerId,
                                         ModelAndView modelAndView) {

        modelAndView.setViewName("worker/updateWorker");

        return modelAndView;
    }


}
