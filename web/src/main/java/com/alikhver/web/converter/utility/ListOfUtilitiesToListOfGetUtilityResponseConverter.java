package com.alikhver.web.converter.utility;

import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListOfUtilitiesToListOfGetUtilityResponseConverter implements Converter<List<Utility>, List<GetUtilityResponse>> {
    private final UtilityToGetUtilityResponseConverter utilityToGetUtilityResponseConverter;

    @Override
    public List<GetUtilityResponse> convert(List<Utility> source) {
        return source.stream()
                .map(utilityToGetUtilityResponseConverter::convert)
                .collect(Collectors.toList());
    }
}
