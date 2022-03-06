package com.alikhver.web.converter.organisation;

import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateOrganisationRequestToRedactorConverter implements Converter<CreateOrganisationRequest, User> {
    @Override
    public User convert(CreateOrganisationRequest request) {
        return User.builder()
                .login(request.getRedactorLogin())
                .password(request.getRedactorPassword())
                .role(UserRole.REDACTOR)
                .build();
    }
}
