package com.alikhver.web.converter.utility;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UtilityToGetUtilityResponseConverter implements Converter<Utility, GetUtilityResponse> {
    @Override
    public GetUtilityResponse convert(Utility utility) {
        return GetUtilityResponse.builder()
                .id(utility.getId())
                .name(utility.getName())
                .description(utility.getDescription())
                .price(utility.getPrice())
                .organisationId(utility.getOrganisation().getId())
                .build();
    }
}
