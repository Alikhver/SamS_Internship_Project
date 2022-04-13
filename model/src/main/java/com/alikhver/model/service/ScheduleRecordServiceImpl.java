package com.alikhver.model.service;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.repository.ScheduleRecordRepository;
import com.alikhver.model.util.ValidationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        var record = repository.findById(recordId);

        log.info("get -> done");
        return record;
    }
}
