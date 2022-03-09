package com.alikhver.model.service;

import com.alikhver.model.repository.ScheduleRecordRepository;
import com.alikhver.model.service.util.ServiceValidationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleRecordServiceImpl implements ScheduleRecordService {
    private final ScheduleRecordRepository repository;
    private final ServiceValidationHelper validationHelper;
}
