package com.alikhver.web.controller;

import com.alikhver.web.facade.UtilityFacade;
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
@RequestMapping("/utility")
@RequiredArgsConstructor
public class UtilityController {
    private final UtilityFacade utilityFacade;

    @GetMapping("/{id}/workers")
    public ModelAndView viewWorkersOfUtility(@PathVariable(name = "id") @Positive Long utilityId,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                             @RequestParam(defaultValue = "5") @Positive int size,
                                             ModelAndView modelAndView) {
        var workers = utilityFacade.getWorkersOfUtility(utilityId, page, size);

        modelAndView.addObject("workers", workers.getContent());
        modelAndView.setViewName("utility-choose-worker");

        return  modelAndView;
    }
}
