package com.alikhver.web.controller;

import com.alikhver.web.facade.ProfileFacade;
import com.alikhver.web.facade.UserFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
@Validated
public class ProfileController {
    private final ProfileFacade profileFacade;
    private final UserFacade userFacade;

    @Value("${hostName}")
    private String hostName;

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

    @GetMapping("/current")
    @ApiOperation("View Active Profile")
    public ModelAndView viewActiveProfile(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
        try {
            URL url = new URL(request.getHeader("Referer"));
            String host = url.getHost();
            String path = url.getPath();

            if (host.equals(hostName) && !path.equals("/profile/current")) {
                session.setAttribute("referer", url.toString());
            } else if (path.equals("/profile/current")) {
                url = new URL((String) session.getAttribute("referer"));
            } else if (!host.equals(hostName)) {
                session.removeAttribute("referer");
            }

            modelAndView.addObject("referer", url);
        } catch (MalformedURLException e) {

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authority = new ArrayList<>(authentication.getAuthorities()).get(0).getAuthority();

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!authority.equals("anonymousUser")) {

        } else {

        }

        String locale = LocaleContextHolder.getLocale().toLanguageTag();
        modelAndView.addObject("locale", locale);

        modelAndView.setViewName("personal-cabinet/personal_cabinet");

        return modelAndView;
    }

}
