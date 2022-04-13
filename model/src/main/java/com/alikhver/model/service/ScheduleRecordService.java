package com.alikhver.model.service;

import com.alikhver.model.entity.ScheduleRecord;

import java.util.Optional;

public interface ScheduleRecordService {
    Optional<ScheduleRecord> get(Long recordId);
}
