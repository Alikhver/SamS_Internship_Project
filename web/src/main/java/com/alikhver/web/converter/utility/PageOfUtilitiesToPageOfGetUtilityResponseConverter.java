package com.alikhver.web.converter.utility;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageOfUtilitiesToPageOfGetUtilityResponseConverter implements Converter<Page<Utility>, Page<GetUtilityResponse>> {
    private final UtilityToGetUtilityResponseConverter utilityToGetUtilityResponseConverter;

    @Override
    public Page<GetUtilityResponse> convert(Page<Utility> utilities) {
        return utilities.map(utilityToGetUtilityResponseConverter::convert);
    }
}
