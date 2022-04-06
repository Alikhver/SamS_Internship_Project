package com.alikhver.web.controller;

import com.alikhver.web.facade.OrganisationFacade;
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
@RequestMapping("/organisation")
@RequiredArgsConstructor
public class OrganisationController {
    private final OrganisationFacade organisationFacade;

    @GetMapping("/{id}")
    public ModelAndView viewOrganisation(@PathVariable @Positive Long id, ModelAndView modelAndView) {
        var org = organisationFacade.getOrganisation(id);
        modelAndView.addObject("org", org);
        modelAndView.setViewName("organisation");
        return modelAndView;
    }

    @GetMapping("/{id}/workers")
    public ModelAndView viewWorkers(@PathVariable @Positive Long id,
                                    @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                    @RequestParam(defaultValue = "5") @Positive int size,
                                    ModelAndView modelAndView) {
        var workers = organisationFacade.getWorkers(id, page, size);

        modelAndView.addObject("workers", workers.getContent());
        modelAndView.addObject("orgId", id);
        modelAndView.setViewName("workers");

        return modelAndView;
    }

    @GetMapping("/{id}/utilities")
    public ModelAndView viewUtilities(@PathVariable @Positive Long id,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                      @RequestParam(defaultValue = "5") @Positive int size,
                                      ModelAndView modelAndView) {
        var utilities= organisationFacade.getUtilities(id, page, size);

        modelAndView.addObject("utilities", utilities.getContent());
        modelAndView.addObject("orgId", id);
        modelAndView.setViewName("utilities");

        return modelAndView;
    }
}
