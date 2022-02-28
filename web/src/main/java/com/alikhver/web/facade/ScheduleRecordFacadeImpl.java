package com.alikhver.web.facade;

import com.alikhver.model.service.ScheduleRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleRecordFacadeImpl implements ScheduleRecordFacade {
    private final ScheduleRecordService scheduleRecordService;
}
