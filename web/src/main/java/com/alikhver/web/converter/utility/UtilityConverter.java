package com.alikhver.web.converter.utility;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UtilityConverter {
    private final CreateUtilityRequestToUtilityConverter createUtilityRequestToUtilityConverter;
    private final UtilityToCreateUtilityResponseConverter utilityToCreateUtilityResponseConverter;
    private final UtilityToGetUtilityResponseConverter utilityToGetUtilityResponseConverter;
    private final PageOfUtilitiesToPageOfGetUtilityResponseConverter pageOfUtilitiesToPageOfGetUtilityResponseConverter;
    private final ListOfUtilitiesToListOfGetUtilityResponseConverter listOfUtilitiesToListOfGetUtilityResponse;

    public GetUtilityResponse mapToGetUtilityResponse(Utility utility) {
        log.info("mapToGetUtilityResponse -> start");

        var response = utilityToGetUtilityResponseConverter.convert(utility);

        log.info("mapToGetUtilityResponse -> done");
        return response;
    }

    public Utility mapToUtility(CreateUtilityRequest request) {
        log.info("mapToUtility");

        var response = createUtilityRequestToUtilityConverter.convert(request);

                log.info("mapToUtility -> done");
        return response;
    }

    public CreateUtilityResponse mapToCreateUtilityResponse(Utility utility) {
        log.info("mapToCreateUtilityResponse -> start");

        var response = utilityToCreateUtilityResponseConverter.convert(utility);

        log.info("mapToCreateUtilityResponse -> done");
        return response;
    }

    public Page<GetUtilityResponse> mapToPageOfGetUtilityResponse(Page<Utility> utilities) {
        log.info("mapToListOfGetUtilityResponse -> start");

        var response = pageOfUtilitiesToPageOfGetUtilityResponseConverter.convert(utilities);

        log.info("mapToListOfGetUtilityResponse -> done");
        return response;
    }

    public List<GetUtilityResponse> mapToListOfGetUtilityResponse(List<Utility> utilities) {
        log.info("mapToListOfGetUtilityResponse -> start");

        var response = listOfUtilitiesToListOfGetUtilityResponse.convert(utilities);

        log.info("mapToListOfGetUtilityResponse -> done");
        return response;
    }
}
