package com.alikhver.web.controller;

import com.alikhver.web.facade.ScheduleRecordFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-records")
public class ScheduleRecordRestController {
    public final ScheduleRecordFacade scheduleRecordFacade;
}
