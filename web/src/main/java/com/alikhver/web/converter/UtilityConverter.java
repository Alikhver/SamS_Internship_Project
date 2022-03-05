package com.alikhver.web.converter;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import org.springframework.stereotype.Component;

@Component
public class UtilityConverter {
    public GetUtilityResponse mapToGetUtilityResponse(Utility utility) {
        return GetUtilityResponse.builder()

                .build();
    }
}
