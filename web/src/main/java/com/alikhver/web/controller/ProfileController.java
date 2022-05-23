package com.alikhver.web.controller;

import com.alikhver.web.facade.ProfileFacade;
import com.alikhver.web.facade.UserFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/profile")
@RequiredArgsConstructor
@Validated
public class ProfileController {
    private final ProfileFacade profileFacade;
    private final UserFacade userFacade;

    @GetMapping
    @ApiOperation("Get Profiles")
    public ModelAndView viewProfiles(@RequestParam(defaultValue = "1") @PositiveOrZero int page,
                                     @RequestParam(defaultValue = "5") @Positive int size,
                                     ModelAndView modelAndView) {

        var profiles = profileFacade.getProfiles(page - 1, size);

        int totalPages = profiles.getTotalPages();
        modelAndView.addObject("totalPages", totalPages);

        modelAndView.addObject("page", page);
        modelAndView.addObject("profiles", profiles.getContent());
        modelAndView.setViewName("profile/profiles");

        return modelAndView;
    }

    @GetMapping("/{profileId}/manage")
    @ApiOperation("Manage Profile")
    public ModelAndView viewProfiles(@PathVariable @Positive Long profileId,
                                     ModelAndView modelAndView) {

        var profile = profileFacade.getProfile(profileId);
        var user = userFacade.getUser(profile.getUserId());

        modelAndView.addObject("profile", profile);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("profile/manageProfile");

        return modelAndView;
    }

    @GetMapping("/")
    @ApiOperation("View Active Profile")
    public ModelAndView viewActiveProfile(ModelAndView modelAndView) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getDetails();

        return modelAndView;
    }

}
