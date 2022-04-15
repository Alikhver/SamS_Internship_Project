package com.alikhver.model.service;

import com.alikhver.model.entity.ScheduleRecord;

import java.util.Date;
import java.util.Optional;

public interface ScheduleRecordService {
    Optional<ScheduleRecord> get(Long recordId);

    Long save(ScheduleRecord record);

    boolean existsByWorkerIdAndDate(Long workerId, Date date);
}
