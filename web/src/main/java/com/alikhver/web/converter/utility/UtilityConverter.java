package com.alikhver.web.converter.utility;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilityConverter {
    private final CreateUtilityRequestToUtilityConverter createUtilityRequestToUtilityConverter;
    private final UtilityToCreateUtilityResponseConverter utilityToCreateUtilityResponseConverter;

    public GetUtilityResponse mapToGetUtilityResponse(Utility utility) {
        return GetUtilityResponse.builder()

                .build();
    }

    public Utility mapToUtility(CreateUtilityRequest request) {
        return createUtilityRequestToUtilityConverter.convert(request);
    }

    public CreateUtilityResponse mapToCreateUtilityResponse(Utility utility) {
        return utilityToCreateUtilityResponseConverter.convert(utility);
    }
}
