package com.alikhver.web.converter.profile;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfilesToListOfGetProfileResponseConverter implements Converter<List<Profile>, List<GetProfileResponse>> {
    private final ProfileToGetProfileResponseConverter profileToGetProfileResponseConverter;

    @Override
    public List<GetProfileResponse> convert(List<Profile> profiles) {
        return profiles.stream()
                .map(profileToGetProfileResponseConverter::convert)
                .collect(Collectors.toList());
    }
}

