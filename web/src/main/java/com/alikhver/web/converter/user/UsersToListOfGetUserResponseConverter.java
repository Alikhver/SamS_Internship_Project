package com.alikhver.web.converter.user;

import com.alikhver.model.entity.User;
import com.alikhver.web.dto.user.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsersToListOfGetUserResponseConverter implements Converter<List<User>, List<GetUserResponse>> {
    private final UserToGetUserResponseConverter userToGetUserResponseConverter;

    @Override
    public List<GetUserResponse> convert(List<User> users) {
        return users.stream()
                .map(userToGetUserResponseConverter::convert)
                .collect(Collectors.toList());
    }
}
