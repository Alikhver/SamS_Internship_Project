package com.alikhver.web.facade;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.UserRole;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import com.alikhver.model.service.ProfileService;
import com.alikhver.model.service.ScheduleRecordService;
import com.alikhver.model.service.UtilityService;
import com.alikhver.model.service.WorkerService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.scheduleRecord.ScheduleRecordConverter;
import com.alikhver.web.dto.record.request.CreateRecordRequest;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import com.alikhver.web.exception.profile.NoProfileFoundException;
import com.alikhver.web.exception.scheduleRecord.NoScheduleRecordFoundException;
import com.alikhver.web.exception.user.UsersRoleIsNotUserException;
import com.alikhver.web.exception.utility.NoUtilityFoundException;
import com.alikhver.web.exception.worker.NoWorkerFoundException;
import com.alikhver.web.exception.worker.WorkerDoesNotHaveProvidedUtilityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleRecordFacadeImpl implements ScheduleRecordFacade {
    private final ValidationHelper validationHelper;
    private final ScheduleRecordService scheduleRecordService;
    private final ScheduleRecordConverter scheduleRecordConverter;
    private final UtilityService utilityService;
    private final WorkerService workerService;
    private final ProfileService profileService;

    @Override
    public GetRecordResponse getRecord(Long recordId) {
        log.info("getRecord -> start");

        ScheduleRecord record = scheduleRecordService.get(recordId).orElseThrow(() ->
                new NoScheduleRecordFoundException(
                        "No ScheduleRecord with id = " + recordId + " found"
                )
        );

        var response = scheduleRecordConverter.mapToGetRecordResponse(record);

        log.info("getRecord -> done");
        return response;
    }

    @Override
    @Transactional
    public Long createRecord(CreateRecordRequest request) {
        log.info("createRecord -> start");

        validationHelper.validateForCorrectId(request.getUtilityId(), "UtilityId");
        validationHelper.validateForCorrectId(request.getProfileId(), "ProfileId");
        validationHelper.validateForCorrectId(request.getWorkerId(), "WorkerId");

        Utility utility = utilityService.getUtility(request.getUtilityId()).orElseThrow(() -> {
                    log.warn("NoUtilityFoundException is thrown");
                    throw new NoUtilityFoundException(
                            "No Utility with id = " + request.getUtilityId() + " found");
                }
        );

        Profile profile = profileService.getProfile(request.getProfileId()).orElseThrow(() -> {
            log.warn("NoProfileFoundException is thrown");
            throw new NoProfileFoundException(
                    "No Profile with id = " + request.getProfileId() + " found"
            );
        });

        if (profile.getUser().getRole() != UserRole.USER) {
            log.warn("UsersRoleIsNotUserException thrown");
            throw new UsersRoleIsNotUserException(
                    "Provided Profile's UsersRole is not USER"
            );
        }

        Worker worker = workerService.getWorker(request.getWorkerId()).orElseThrow(() -> {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(
                    "No Worker with id = " + request.getWorkerId() + " found"
            );
        });

        if (!workerService.workerAlreadyHasUtility(worker.getId(), utility.getId())) {
            log.warn("WorkerDoesNotHaveProvidedUtilityException is thrown");
            throw new WorkerDoesNotHaveProvidedUtilityException(
                    "Worker with id = " + worker.getId() + "does not have Utility with id " + utility.getId()
            );
        }

        ScheduleRecord record = scheduleRecordConverter.mapToScheduleRecord(request);

        record.setUtility(utility);
        record.setClientProfile(profile);
        record.setWorker(worker);

        Long recordId = scheduleRecordService.save(record);

        log.info("createRecord -> done");
        return recordId;
    }
}
