package com.alikhver.web.converter.utility;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UtilityToCreateUtilityResponseConverter implements Converter<Utility, CreateUtilityResponse> {
    @Override
    public CreateUtilityResponse convert(Utility utility) {
        return CreateUtilityResponse.builder()
                .id(utility.getId())
                .name(utility.getName())
                .price(utility.getPrice())
                .description(utility.getDescription())
                .build();
    }
}
