package com.alikhver.web.restcontroller;

import com.alikhver.web.facade.ScheduleRecordFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-records")
@Validated
public class ScheduleRecordRestController {
    public final ScheduleRecordFacade scheduleRecordFacade;
}
