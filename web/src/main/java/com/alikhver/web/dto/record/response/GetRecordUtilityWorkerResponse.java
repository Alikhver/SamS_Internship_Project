package com.alikhver.web.dto.record.response;

import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class GetRecordUtilityWorkerResponse {
        private GetRecordResponse record;
        private GetUtilityResponse utility;
        private GetWorkerResponse worker;
}
