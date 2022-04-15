package com.alikhver.web.dto.record.response;

import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
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
