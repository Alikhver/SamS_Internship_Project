package com.alikhver.web.converter.profile;

import com.alikhver.model.entity.Profile;
import com.alikhver.web.dto.profile.response.GetProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageOfProfilesToPageOfGetProfileResponseConverter implements Converter<Page<Profile>, Page<GetProfileResponse>> {
    private final ProfileToGetProfileResponseConverter profileToGetProfileResponseConverter;

    @Override
    public Page<GetProfileResponse> convert(Page<Profile> profiles) {
        return profiles
                .map(profileToGetProfileResponseConverter::convert);
    }
}

