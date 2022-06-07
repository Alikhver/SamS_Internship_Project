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
import com.alikhver.web.exception.scheduleRecord.RecordCannotBeBookedException;
import com.alikhver.web.exception.scheduleRecord.ScheduleRecordWithSuchWorkerAndTimeAlreadyExists;
import com.alikhver.web.exception.scheduleRecord.RecordIsAlreadyAvailableException;
import com.alikhver.web.exception.user.UsersRoleIsNotUserException;
import com.alikhver.web.exception.utility.NoUtilityFoundException;
import com.alikhver.web.exception.worker.NoWorkerFoundException;
import com.alikhver.web.exception.worker.WorkerDoesNotHaveProvidedUtilityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduleRecordFacadeImpl implements ScheduleRecordFacade {
    @Autowired
    private ValidationHelper validationHelper;
    @Autowired
    private ScheduleRecordService scheduleRecordService;
    @Autowired
    private ScheduleRecordConverter scheduleRecordConverter;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private ProfileService profileService;

    @Override
    public GetRecordResponse getRecord(Long recordId) {
        log.info("getRecord -> start");

        ScheduleRecord record = scheduleRecordService.get(recordId).orElseThrow(() -> {
            log.warn("NoScheduleRecordFoundException is thrown");
            throw new NoScheduleRecordFoundException(recordId);
        });

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
            throw new NoWorkerFoundException(request.getWorkerId());
        });

        ScheduleRecord record = scheduleRecordConverter.mapToScheduleRecord(request);

        if (scheduleRecordService.existsByWorkerIdAndDateAndStatus(worker.getId(), record.getDate(), ScheduleRecordStatus.AVAILABLE)) {
            log.warn("ScheduleRecordWithSuchWorkerAndTimeAlreadyExists is thrown");
            throw new ScheduleRecordWithSuchWorkerAndTimeAlreadyExists();
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
                    throw new NoUtilityFoundException(utilityId);
                }
        );

        Profile profile = profileService.getProfile(profileId).orElseThrow(() -> {
            log.warn("NoProfileFoundException is thrown");
            throw new NoProfileFoundException(profileId);
        });

        if (profile.getUser().getRole() != UserRole.USER) {
            log.warn("UsersRoleIsNotUserException thrown");
            throw new UsersRoleIsNotUserException();
        }


        ScheduleRecord record = scheduleRecordService.get(recordId).orElseThrow(() -> {
            log.warn("NoScheduleRecordFoundException is thrown");
            throw new NoScheduleRecordFoundException(recordId);
        });

        if (!record.getStatus().equals(ScheduleRecordStatus.AVAILABLE)) {
            log.warn("RecordCannotBeBookedException is thrown");
            throw new RecordCannotBeBookedException(recordId);
        }

        Worker worker = record.getWorker();

        if (!workerService.workerAlreadyHasUtility(worker.getId(), utility.getId())) {
            log.warn("WorkerDoesNotHaveProvidedUtilityException is thrown");
            throw new WorkerDoesNotHaveProvidedUtilityException();
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
            throw new NoScheduleRecordFoundException(recordId);
        });

        if (record.getUtility() == null && record.getClientProfile() == null &&
                record.getStatus() == ScheduleRecordStatus.AVAILABLE) {
            log.warn("Record with id = " + record + " is already Available");
            throw new RecordIsAlreadyAvailableException();
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
            throw new NoScheduleRecordFoundException(recordId);
        });

        record.setStatus(ScheduleRecordStatus.CANCELED);

        scheduleRecordService.save(record);

        log.info("cancelRecord -> done");
    }

    @Override
    @Transactional
    public void cancelRecords(CancelRecordsRequest request) {
        log.info("cancelRecords -> start");

        LocalDate date = request.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int startTime = request.getStartTime();
        int endTime = request.getEndTime();
        Long workerId = request.getWorkerId();

        if (!workerService.existsWorker(workerId)) {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(workerId);
        }


        for (; startTime < endTime; startTime++) {
            LocalDateTime time = date.atTime(startTime, 0);
            var optionalRecord = scheduleRecordService.getRecordByWorkerIdAndDateAndStatus(workerId,
                    Date.from(time.atZone(ZoneId.systemDefault()).toInstant()),
                    ScheduleRecordStatus.AVAILABLE);
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

        LocalDate date = request.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int startTime = request.getStartTime();
        int endTime = request.getEndTime();
        Long workerId = request.getWorkerId();

        Worker worker = workerService.getWorker(workerId).orElseThrow(() -> {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(workerId);
        });

        for (; startTime < endTime; startTime++) {
            LocalDateTime time = date.atTime(startTime, 0);
            Date dt = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());

            if (!scheduleRecordService.existsByWorkerIdAndDateAndStatus(workerId, dt, ScheduleRecordStatus.AVAILABLE)) {
                ScheduleRecord record = ScheduleRecord.builder()
                        .worker(worker)
                        .status(ScheduleRecordStatus.AVAILABLE)
                        .date(dt)
                        .build();

                scheduleRecordService.save(record);
            }
        }

        log.info("createRecords -> done");
    }

    @Override
    @Transactional
    public void setRecordExpired(Long recordId) {
        log.info("setRecordExpired -> start");

        validationHelper.validateForCorrectId(recordId, "RecordId");

        ScheduleRecord record = scheduleRecordService.get(recordId).orElseThrow(() -> {
            log.warn("NoScheduleRecordFoundException is thrown");
            throw new NoScheduleRecordFoundException(recordId);
        });

        record.setStatus(ScheduleRecordStatus.EXPIRED);

        scheduleRecordService.save(record);

        log.info("setRecordExpired -> done");
    }

    @Override
    @Transactional
    public void setRecordDone(Long recordId) {
        log.info("setRecordDone -> start");

        validationHelper.validateForCorrectId(recordId, "RecordId");

        ScheduleRecord record = scheduleRecordService.get(recordId).orElseThrow(() -> {
            log.warn("NoScheduleRecordFoundException is thrown");
            throw new NoScheduleRecordFoundException(recordId);
        });

        record.setStatus(ScheduleRecordStatus.DONE);

        scheduleRecordService.save(record);

        log.info("setRecordDone -> done");
    }

    @Override
    @Transactional
    public List<GetRecordResponse> getRecordsOfDay(Long workerId, LocalDate start) {
        log.info("getRecordsOfDay -> start");

        LocalDate end = start.plusDays(1);

        List<ScheduleRecord> records = scheduleRecordService.findAllRecordsOfWorkerByTime(workerId, start, end).stream()
                .filter(record -> record.getStatus().equals(ScheduleRecordStatus.AVAILABLE) || record.getStatus().equals(ScheduleRecordStatus.BOOKED))
                .collect(Collectors.toList());


        var response = scheduleRecordConverter.mapToListOfGetRecordResponse(records);

        log.info("getRecordsOfDay -> done");
        return response;
    }

    @Override
    public List<GetRecordResponse> getAvailableRecordsOfDay(Long workerId, LocalDateTime start) {
        log.info("getAvailableRecordsOfDay -> start");

        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = startDate.plusDays(1);

        List<ScheduleRecord> records = scheduleRecordService.findAllRecordsOfWorkerByTime(workerId, startDate, endDate).stream()
                .filter(record -> record.getStatus().equals(ScheduleRecordStatus.AVAILABLE))
                .sorted(Comparator.comparing(ScheduleRecord::getDate))
                .collect(Collectors.toList());

        List<GetRecordResponse> response = scheduleRecordConverter.mapToListOfGetRecordResponse(records);

        log.info("getAvailableRecordsOfDay -> done");
        return response;
    }
}
