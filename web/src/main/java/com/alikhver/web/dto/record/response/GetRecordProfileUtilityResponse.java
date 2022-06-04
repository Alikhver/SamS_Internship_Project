package com.alikhver.web.dto.record.response;

import com.alikhver.web.dto.profile.response.GetProfileResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class GetRecordProfileUtilityResponse {
    private GetRecordResponse record;
    private GetProfileResponse profile;
    private GetUtilityResponse utility;
}
