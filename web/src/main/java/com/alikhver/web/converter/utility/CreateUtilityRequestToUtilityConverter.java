package com.alikhver.web.converter.utility;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateUtilityRequestToUtilityConverter implements Converter<CreateUtilityRequest, Utility> {
    @Override
    public Utility convert(CreateUtilityRequest request) {
        return Utility.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }
}
