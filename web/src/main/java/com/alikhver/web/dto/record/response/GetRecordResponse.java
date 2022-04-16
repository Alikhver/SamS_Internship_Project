package com.alikhver.web.dto.record.response;

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
    private Long profileId;
    private Long workerId;
    private Long utilityId;
}
