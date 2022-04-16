package com.alikhver.model.service;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.ScheduleRecordStatus;
import com.alikhver.model.repository.ScheduleRecordRepository;
import com.alikhver.model.util.ValidationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleRecordServiceImpl implements ScheduleRecordService {
    private final ScheduleRecordRepository repository;
    private final ValidationHelper validationHelper;

    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleRecord> get(Long recordId) {
        log.info("get -> start");

        validationHelper.validateForCorrectId(recordId, "RecordId");
        var record = repository.findById(recordId);

        log.info("get -> done");
        return record;
    }

    @Override
    public Long save(ScheduleRecord record) {
        log.info("save -> start");

        repository.save(record);

        log.info("save -> done");
        return record.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByWorkerIdAndDateAndStatus(Long workerId, Date date, ScheduleRecordStatus status) {
        log.info("existsByWorkerIdAndDate -> start");

        validationHelper.validateForCorrectId(workerId, "WorkerId");
        var exists = repository.existsByWorkerIdAndDateAndStatus(workerId, date, status);

        log.info("existsByWorkerIdAndDate -> done");
        return exists;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleRecord> findAllRecordsOfWorker(Long workerId) {
        log.info("findAllRecordsOfWorker -> start");

        validationHelper.validateForCorrectId(workerId, "WorkerId");
        var response = repository.findAllByWorkerId(workerId);

        log.info("findAllRecordsOfWorker -> done");
        return response;
    }
}
