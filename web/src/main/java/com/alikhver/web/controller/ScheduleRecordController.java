package com.alikhver.web.controller;

import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.dto.record.response.GetRecordProfileUtilityResponse;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.exception.worker.WorkerDoesNotBelongToOrganisationException;
import com.alikhver.web.facade.OrganisationFacade;
import com.alikhver.web.facade.ProfileFacade;
import com.alikhver.web.facade.ScheduleRecordFacade;
import com.alikhver.web.facade.UtilityFacade;
import com.alikhver.web.facade.WorkerFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Positive;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/organisation/{orgId}")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ScheduleRecordController {
    private final OrganisationFacade organisationFacade;
    private final WorkerFacade workerFacade;
    private final ScheduleRecordFacade recordFacade;
    private final UtilityFacade utilityFacade;
    private final ProfileFacade profileFacade;

    @GetMapping("/select-worker")
    @PreAuthorize("permitAll()")
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
    @PreAuthorize("hasAuthority('REDACTOR')")
    @ApiOperation("Assign Schedule")
    public ModelAndView viewAssignSchedule(@PathVariable @Positive Long orgId,
                                           @PathVariable @Positive Long workerId,
                                           @RequestParam(required = false) Date date,
                                           ModelAndView modelAndView) {
        var worker = workerFacade.getWorkerById(workerId);
        var organisation = organisationFacade.getOrganisation(orgId);
        List<GetRecordProfileUtilityResponse> recordData = new ArrayList<>();

        if (!Objects.isNull(date)) {
            LocalDate start = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<GetRecordResponse> records = recordFacade.getRecordsOfDay(workerId, start);

            recordData = records.stream().map(record -> {
                        GetRecordProfileUtilityResponse recordInfo = GetRecordProfileUtilityResponse.builder().build();

                        recordInfo.setRecord(record);

                        Optional.ofNullable(record.getUtilityId()).ifPresentOrElse(
                                utilityId -> recordInfo.setUtility(utilityFacade.getUtility(utilityId)),
                                () -> recordInfo.setUtility(GetUtilityResponse.builder().build()));

                        Optional.ofNullable(record.getProfileId()).ifPresentOrElse(
                                profileId -> recordInfo.setProfile(profileFacade.getProfileById(profileId)),
                                () -> recordInfo.setProfile(GetProfileResponse.builder().build()));

                        return recordInfo;
                    })
                    .sorted(Comparator.comparing(o -> o.getRecord().getDate()))
                    .collect(Collectors.toList());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String dateResult = dateFormat.format(date);

            dateFormat = new SimpleDateFormat("HH:mm");

            modelAndView.addObject("dateFormat", dateFormat);
            modelAndView.addObject("date", dateResult);
        }

        modelAndView.addObject("worker", worker);
        modelAndView.addObject("org", organisation);
        modelAndView.addObject("recordData", recordData);

        modelAndView.setViewName("select-time/assignSchedule");

        return modelAndView;
    }

    @GetMapping("/records")
    @ApiOperation("Select records of worker")
    public ModelAndView selectRecordsOfWorker(@PathVariable @Positive Long orgId,
                                              @RequestParam("worker") @Positive Long workerId,
                                              @RequestParam(required = false) Date date,
                                              ModelAndView modelAndView) {
        GetWorkerResponse worker = workerFacade.getWorkerById(workerId);

        if (worker.getOrganisationId() != orgId) {
            log.warn("WorkerDoesNotBelongToOrganisationException is thrown");
            throw new WorkerDoesNotBelongToOrganisationException(
                    "Worker with id = " + workerId + " does not belong to Organisation with id = " + orgId);
        }


        List<GetRecordResponse> records = new ArrayList<>();

        if (!Objects.isNull(date)) {
            LocalDate start = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            records = recordFacade.getAvailableRecordsOfDay(workerId, start.atStartOfDay());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", LocaleContextHolder.getLocale());
            String dateResult = dateFormat.format(date);

            dateFormat = new SimpleDateFormat("HH:mm");

            modelAndView.addObject("dateFormat", dateFormat);
            modelAndView.addObject("date", dateResult);
        }

        modelAndView.addObject("worker", worker);
        modelAndView.addObject("records", records);

        modelAndView.setViewName("select-time/select-time");

        return modelAndView;
    }
}
