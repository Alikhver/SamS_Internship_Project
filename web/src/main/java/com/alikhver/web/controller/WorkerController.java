package com.alikhver.web.controller;

import com.alikhver.web.facade.WorkerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping("/worker")
@RequiredArgsConstructor
public class WorkerController {
    private final WorkerFacade workerFacade;

    @GetMapping("/{id}/utilities")
    public ModelAndView viewUtilitiesOfWorker(@PathVariable(name = "id") @Positive Long workerId,
                                              @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                              @RequestParam(defaultValue = "5") @Positive int size,
                                              ModelAndView modelAndView) {

        var utilities = workerFacade.getUtilitiesOfWorker(workerId, page, size);

        modelAndView.addObject("utilities", utilities.getContent());
        modelAndView.setViewName("worker-choose-utility");

        return modelAndView;
    }

}
