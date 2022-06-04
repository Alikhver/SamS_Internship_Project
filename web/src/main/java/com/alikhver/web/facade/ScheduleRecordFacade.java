package com.alikhver.web.facade;

import com.alikhver.web.dto.record.request.CancelRecordsRequest;
import com.alikhver.web.dto.record.request.CreateRecordRequest;
import com.alikhver.web.dto.record.request.CreateRecordsRequest;
import com.alikhver.web.dto.record.response.GetRecordProfileUtilityResponse;
import com.alikhver.web.dto.record.response.GetRecordResponse;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRecordFacade {
    GetRecordResponse getRecord(Long recordId);

    Long createRecord(CreateRecordRequest request);

    void bookRecord(Long recordId, Long utilityId, Long profileId);

    void releaseRecord(Long recordId);

    void cancelRecord(Long recordId);

    void cancelRecords(CancelRecordsRequest request);

    void createRecords(CreateRecordsRequest request);

    void setRecordExpired(Long recordId);

    void setRecordDone(Long recordId);

    List<GetRecordResponse> getRecordsOfDay(Long workerId, LocalDate start);

    List<GetRecordProfileUtilityResponse> getFullRecordData(List<GetRecordResponse> records);
}
