package com.alikhver.web.converter.utility;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UtilityConverter {
    private final CreateUtilityRequestToUtilityConverter createUtilityRequestToUtilityConverter;
    private final UtilityToCreateUtilityResponseConverter utilityToCreateUtilityResponseConverter;
    private final UtilityToGetUtilityResponseConverter utilityToGetUtilityResponseConverter;
    private final UtilitiesToListOfGetUtilityResponseConverter utilitiesToListOfGetUtilityResponseConverter;

    public GetUtilityResponse mapToGetUtilityResponse(Utility utility) {
        return utilityToGetUtilityResponseConverter.convert(utility);
    }

    public Utility mapToUtility(CreateUtilityRequest request) {
        return createUtilityRequestToUtilityConverter.convert(request);
    }

    public CreateUtilityResponse mapToCreateUtilityResponse(Utility utility) {
        return utilityToCreateUtilityResponseConverter.convert(utility);
    }

    public List<GetUtilityResponse> mapToListOfGetUtilityResponse(List<Utility> utilities) {
        return utilitiesToListOfGetUtilityResponseConverter.convert(utilities);
    }
}
