package com.alikhver.web.controller;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.record.response.GetRecordUtilityWorkerResponse;
import com.alikhver.web.facade.ProfileFacade;
import com.alikhver.web.facade.UserFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation("Manage Profile")
    public ModelAndView viewProfiles(@PathVariable @Positive Long profileId,
                                     ModelAndView modelAndView) {

        var profile = profileFacade.getProfileById(profileId);
        var user = userFacade.getUser(profile.getUserId());

        modelAndView.addObject("profile", profile);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("profile/manageProfile");

        return modelAndView;
    }

    @GetMapping("/current")
    @PreAuthorize("permitAll()")
    @ApiOperation("View Active Profile")
    public ModelAndView viewActiveProfile(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
        try {
            URL url = new URL(request.getHeader("Referer"));
            String host = url.getHost();
            String path = url.getPath();

            if (
                    host.equals(hostName) &&
                            !path.equals("/profile/current") &&
                            !path.equals("/profile/update") &&
                            !path.equals("/register") &&
                            !path.equals("/profile/records")
//                            &&
//                            !path.equals("/")

            ) {
                session.setAttribute("referer", url.toString());
            } else if (path.equals("/profile/current")) {
                url = new URL((String) session.getAttribute("referer"));
            } else if (!host.equals(hostName)) {
                session.setAttribute("referer", "/");
            }

            modelAndView.addObject("referer", url);
        } catch (MalformedURLException e) {

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authority = new ArrayList<>(authentication.getAuthorities()).get(0).getAuthority();

        String locale = LocaleContextHolder.getLocale().toLanguageTag();
        modelAndView.addObject("locale", locale);

        modelAndView.setViewName("personal-cabinet/personal_cabinet");
        //TODO fix on back from /profile/update null referer

        return modelAndView;
    }

    @GetMapping("/update")
    @ApiOperation("Update profile by USER")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView updateProfile(Authentication authentication, ModelAndView modelAndView) {

        String login = ((User) authentication.getPrincipal()).getUsername();

        var user = userFacade.findByLogin(login);
        Profile profile = profileFacade.getProfileByLogin(login);


        modelAndView.addObject("profile", profile);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("profile/updateProfile");

        return modelAndView;
    }

    @GetMapping("/records")
    @PreAuthorize("hasAuthority('USER')")
    @ApiOperation("View Records of profile")
    public ModelAndView viewRecordsOfProfile(ModelAndView modelAndView) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<GetRecordUtilityWorkerResponse> recordData = profileFacade.getRecordDataOfProfile(user.getUsername());



        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm", LocaleContextHolder.getLocale());

        modelAndView.addObject("dateFormat", dateFormat);
        modelAndView.addObject("recordData", recordData);
        modelAndView.setViewName("profile/recordsOfProfile");

        return modelAndView;
    }

}
