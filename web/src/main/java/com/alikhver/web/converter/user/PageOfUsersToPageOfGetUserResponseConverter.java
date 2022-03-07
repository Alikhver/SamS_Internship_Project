package com.alikhver.web.converter.user;

import com.alikhver.model.entity.User;
import com.alikhver.web.dto.user.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageOfUsersToPageOfGetUserResponseConverter implements Converter<Page<User>, Page<GetUserResponse>> {
    private final UserToGetUserResponseConverter userToGetUserResponseConverter;

    @Override
    public Page<GetUserResponse> convert(Page<User> users) {
        return users.map(userToGetUserResponseConverter::convert);
    }
}
