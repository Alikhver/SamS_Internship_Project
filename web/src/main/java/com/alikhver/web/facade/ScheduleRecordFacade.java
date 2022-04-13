package com.alikhver.web.facade;

import com.alikhver.web.dto.record.response.GetRecordResponse;

public interface ScheduleRecordFacade {
    GetRecordResponse getRecord(Long recordId);
}
