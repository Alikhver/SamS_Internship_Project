package com.alikhver.model.service;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.ScheduleRecordStatus;
import com.alikhver.model.repository.ScheduleRecordRepository;
import com.alikhver.model.util.ValidationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
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
        log.info("existsByWorkerIdAndDateAndStatus -> start");

        validationHelper.validateForCorrectId(workerId, "WorkerId");
        var exists = repository.existsByWorkerIdAndDateAndStatus(workerId, date, status);

        log.info("existsByWorkerIdAndDateAndStatus -> done");
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

    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleRecord> getRecordByWorkerIdAndDateAndStatus(Long workerId, Date date, ScheduleRecordStatus status) {
        log.info("getRecordByWorkerIdAndDateAndStatus -> start");

        var response = repository.findByWorkerIdAndDateAndStatus(workerId, date, status);

        log.info("getRecordByWorkerIdAndDateAndStatus -> done");
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleRecord> getUtilitiesByTime(Date current) {
        log.info("getUtilitiesByTime -> start");

        List<ScheduleRecord> records = repository.findAllByDate(current);

        log.info("getUtilitiesByTime -> done");
        return records;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleRecord> findAllRecordsOfWorkersAfterDate(Long recordId, Date date) {
        log.info("findAllRecordsOfWorkersAfterDate -> start");

        validationHelper.validateForCorrectId(recordId, "RecordId");

        var response = repository.findAllByWorkerIdAndDateAfter(recordId, date);

        log.info("findAllRecordsOfWorkersAfterDate -> done");
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleRecord> findAllRecordsOfWorkerByTime(Long workerId, LocalDate start, LocalDate end) {
        log.info("findAllRecordsOfWorkerByTime -> start");

        Date startDate = Date.from(start.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        Date endDate = Date.from(end.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        List<ScheduleRecord> records = repository.findRecordsOfTime(workerId, startDate, endDate);

        log.info("findAllRecordsOfWorkerByTime -> done");
        return records;
    }
}
