package com.alikhver.model.service;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.ScheduleRecordStatus;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleRecordService {
    Optional<ScheduleRecord> get(Long recordId);

    Long save(ScheduleRecord record);

    boolean existsByWorkerIdAndDateAndStatus(Long workerId, Date date, ScheduleRecordStatus status);

    List<ScheduleRecord> findAllRecordsOfWorker(Long workerId);

    Optional<ScheduleRecord> getRecordByWorkerIdAndDateAndStatus(Long workerId, Date date, ScheduleRecordStatus available);

    List<ScheduleRecord> getUtilitiesByTime(Date current);

    List<ScheduleRecord> findAllRecordsOfWorkersAfterDate(Long id, Date date);

    List<ScheduleRecord> findAllRecordsOfWorkerByTimeAndStatus(Long workerId, LocalDate start, LocalDate end);
}
