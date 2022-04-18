package com.alikhver.web.facade;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.ScheduleRecordStatus;
import com.alikhver.model.entity.UserRole;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import com.alikhver.model.service.ProfileService;
import com.alikhver.model.service.ScheduleRecordService;
import com.alikhver.model.service.UtilityService;
import com.alikhver.model.service.WorkerService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.scheduleRecord.ScheduleRecordConverter;
import com.alikhver.web.dto.record.request.CancelRecordsRequest;
import com.alikhver.web.dto.record.request.CreateRecordRequest;
import com.alikhver.web.dto.record.request.CreateRecordsRequest;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import com.alikhver.web.exception.profile.NoProfileFoundException;
import com.alikhver.web.exception.scheduleRecord.NoScheduleRecordFoundException;
import com.alikhver.web.exception.scheduleRecord.ScheduleRecordWithSuchWorkerAndTimeAlreadyExists;
import com.alikhver.web.exception.scheduleRecord.UtilityIsAlreadyAvailableException;
import com.alikhver.web.exception.user.UsersRoleIsNotUserException;
import com.alikhver.web.exception.utility.NoUtilityFoundException;
import com.alikhver.web.exception.worker.NoWorkerFoundException;
import com.alikhver.web.exception.worker.WorkerDoesNotHaveProvidedUtilityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

        Worker worker = workerService.getWorker(request.getWorkerId()).orElseThrow(() -> {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(
                    "No Worker with id = " + request.getWorkerId() + " found"
            );
        });

        ScheduleRecord record = scheduleRecordConverter.mapToScheduleRecord(request);

        if (scheduleRecordService.existsByWorkerIdAndDateAndStatus(worker.getId(), record.getDate(), ScheduleRecordStatus.AVAILABLE)) {
            log.warn("ScheduleRecordWithSuchWorkerAndTimeAlreadyExists is thrown");
            throw new ScheduleRecordWithSuchWorkerAndTimeAlreadyExists(
                    "ScheduleRecord with workerId = " + worker.getId() + " and date = " + record.getDate() + " already exists"
            );
        }

        record.setWorker(worker);

        Long recordId = scheduleRecordService.save(record);

        log.info("createRecord -> done");
        return recordId;
    }

    @Override
    @Transactional
    public void bookRecord(Long recordId, Long utilityId, Long profileId) {
        log.info("bookRecord -> start");


        validationHelper.validateForCorrectId(recordId, "RecordId");
        validationHelper.validateForCorrectId(utilityId, "UtilityId");
        validationHelper.validateForCorrectId(profileId, "ProfileId");

        Utility utility = utilityService.getUtility(utilityId).orElseThrow(() -> {
                    log.warn("NoUtilityFoundException is thrown");
                    throw new NoUtilityFoundException(
                            "No Utility with id = " + utilityId + " found");
                }
        );

        Profile profile = profileService.getProfile(profileId).orElseThrow(() -> {
            log.warn("NoProfileFoundException is thrown");
            throw new NoProfileFoundException(
                    "No Profile with id = " + profileId + " found"
            );
        });

        if (profile.getUser().getRole() != UserRole.USER) {
            log.warn("UsersRoleIsNotUserException thrown");
            throw new UsersRoleIsNotUserException(
                    "Provided Profile's UsersRole is not USER"
            );
        }


        ScheduleRecord record = scheduleRecordService.get(recordId).orElseThrow(() -> {
            log.warn("NoScheduleRecordFoundException is thrown");
            throw new NoScheduleRecordFoundException(
                    "No ScheduleRecord with id = " + recordId + "found"
            );
        });

        Worker worker = record.getWorker();

        if (!workerService.workerAlreadyHasUtility(worker.getId(), utility.getId())) {
            log.warn("WorkerDoesNotHaveProvidedUtilityException is thrown");
            throw new WorkerDoesNotHaveProvidedUtilityException(
                    "Worker with id = " + worker.getId() + "does not have Utility with id " + utility.getId()
            );
        }

        record.setUtility(utility);
        record.setClientProfile(profile);
        record.setStatus(ScheduleRecordStatus.BOOKED);

        scheduleRecordService.save(record);
        log.info("bookRecord -> done");
    }

    @Override
    @Transactional
    public void releaseRecord(Long recordId) {
        log.info("releaseRecord -> start");

        validationHelper.validateForCorrectId(recordId, "RecordId");

        ScheduleRecord record = scheduleRecordService.get(recordId).orElseThrow(() -> {
            log.warn("NoScheduleRecordFoundException is thrown");
            throw new NoScheduleRecordFoundException(
              "No ScheduleRecord with id = " + recordId + " found"
            );
        });

        if (record.getUtility() == null && record.getClientProfile() == null &&
                record.getStatus() == ScheduleRecordStatus.AVAILABLE) {
            log.warn("Utility with id = " + record + " is already Available");
            throw new UtilityIsAlreadyAvailableException(
              "Utility with id = " + record + " is already Available"
            );
        } else {
            record.setUtility(null);
            record.setClientProfile(null);
            record.setStatus(ScheduleRecordStatus.AVAILABLE);

            scheduleRecordService.save(record);
        }

        log.info("releaseRecord -> done");
    }

    @Override
    @Transactional
    public void cancelRecord(Long recordId) {
        log.info("cancelRecord -> start");

        validationHelper.validateForCorrectId(recordId, "RecordId");

        ScheduleRecord record = scheduleRecordService.get(recordId).orElseThrow(() -> {
            log.warn("NoScheduleRecordFoundException is thrown");
            throw new NoScheduleRecordFoundException(
                    "No ScheduleRecord with id = " + recordId + " found"
            );
        });

        record.setStatus(ScheduleRecordStatus.CANCELED);

        scheduleRecordService.save(record);

        log.info("cancelRecord -> done");
    }

    @Override
    @Transactional
    public void cancelRecords(CancelRecordsRequest request) {
        log.info("cancelRecords -> start");

        Date date = request.getDate();
        int startTime = request.getStartTime();
        int endTime = request.getEndTime();
        Long workerId = request.getWorkerId();

        if (!workerService.existsWorker(workerId)) {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(
              "No Worker with id = " + workerId + " found"
            );
        }


        for (; startTime < endTime; startTime++) {
            date.setHours(startTime);
            var optionalRecord = scheduleRecordService.getRecordByWorkerIdAndDateAndStatus(workerId, date, ScheduleRecordStatus.AVAILABLE);
            ScheduleRecord record;

            if (optionalRecord.isPresent()) {
                record = optionalRecord.get();
                cancelRecord(record.getId());
            }
        }

        log.info("cancelRecords -> done");
    }

    @Override
    @Transactional
    public void createRecords(CreateRecordsRequest request) {
        log.info("createRecords -> start");

        Date date = request.getDate();
        int startTime = request.getStartTime();
        int endTime = request.getEndTime();
        Long workerId = request.getWorkerId();

        Worker worker = workerService.getWorker(workerId).orElseThrow(() -> {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(
                    "No Worker with id = " + workerId + " found"
            );
        });

        for (; startTime < endTime; startTime++) {
            date.setHours(startTime);
            Date dt = (Date) date.clone();

            if (!scheduleRecordService.existsByWorkerIdAndDateAndStatus(workerId, dt, ScheduleRecordStatus.AVAILABLE)) {
                ScheduleRecord record = ScheduleRecord.builder()
                        .worker(worker)
                        .status(ScheduleRecordStatus.AVAILABLE)
                        .date(dt)
                        .build();

                scheduleRecordService.save(record);
            }

//            if (!scheduleRecordService.existsByWorkerIdAndDateAndStatus(workerId, date, ScheduleRecordStatus.AVAILABLE)) {
//                CreateRecordRequest record = CreateRecordRequest.builder()
//                        .workerId(workerId)
//                        .date(date)
//                        .build();
//
//                createRecord(record);
//            }
        }

        log.info("createRecords -> done");
    }
}
