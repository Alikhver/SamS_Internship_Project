package com.alikhver.web.dto.record.response;

import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class GetRecordResponse {
    private Long id;
    private Date date;
    private String status;
    private GetProfileResponse profile;
    private GetWorkerResponse worker;
    private GetUtilityResponse utility;
}
