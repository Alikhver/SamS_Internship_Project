package com.alikhver.web.converter.utility;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilityConverter {
    private final CreateUtilityRequestToUtilityConverter createUtilityRequestToUtilityConverter;
    private final UtilityToCreateUtilityResponseConverter utilityToCreateUtilityResponseConverter;
    private final UtilityToGetUtilityResponseConverter utilityToGetUtilityResponseConverter;
    private final PageOfUtilitiesToPageOfGetUtilityResponseConverter pageOfUtilitiesToPageOfGetUtilityResponseConverter;

    public GetUtilityResponse mapToGetUtilityResponse(Utility utility) {
        return utilityToGetUtilityResponseConverter.convert(utility);
    }

    public Utility mapToUtility(CreateUtilityRequest request) {
        return createUtilityRequestToUtilityConverter.convert(request);
    }

    public CreateUtilityResponse mapToCreateUtilityResponse(Utility utility) {
        return utilityToCreateUtilityResponseConverter.convert(utility);
    }

    public Page<GetUtilityResponse> mapToListOfGetUtilityResponse(Page<Utility> utilities) {
        return pageOfUtilitiesToPageOfGetUtilityResponseConverter.convert(utilities);
    }
}
