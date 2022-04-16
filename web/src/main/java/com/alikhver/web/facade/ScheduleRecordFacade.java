package com.alikhver.web.facade;

import com.alikhver.web.dto.record.request.CreateRecordRequest;
import com.alikhver.web.dto.record.response.GetRecordResponse;

public interface ScheduleRecordFacade {
    GetRecordResponse getRecord(Long recordId);

    Long createRecord(CreateRecordRequest request);

    void bookRecord(Long recordId, Long utilityId, Long profileId);

    void releaseRecord(Long recordId);

    void cancelRecord(Long recordId);
}
