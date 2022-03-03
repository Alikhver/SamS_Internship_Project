package com.alikhver.web.converter;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganisationConverter {
    public GetOrganisationResponse mapToGetOrganisationResponse(Organisation organisation) {
        return GetOrganisationResponse.builder()
                .name(organisation.getName())
                .description(organisation.getDescription())
                .address(organisation.getAddress())
                .utilities(organisation.getUtilities())
                .workers(organisation.getWorkers())
                .build();
    }

    public List<GetOrganisationResponse> mapToListOfGetOrganisationResponse(List<Organisation> orgs) {
        return orgs.stream()
                .map(this::mapToGetOrganisationResponse)
                .collect(Collectors.toList());
    }

    public Organisation mapToOrganisation(CreateOrganisationRequest request) {
        return Organisation.builder()
                .name(request.getName())
                .address(request.getAddress())
                .description(request.getDescription())
                .build();
    }

    public User mapToRedactor(CreateOrganisationRequest request) {
        return User.builder()
                .login(request.getRedactorLogin())
                .password(request.getRedactorPassword())
                .role(UserRole.REDACTOR)
                .build();
    }

    public CreateOrganisationResponse mapToCreateOrganisationResponse(Organisation organisation) {
        return CreateOrganisationResponse.builder()
                .name(organisation.getName())
                .description(organisation.getDescription())
                .address(organisation.getAddress())
                .build();
    }
}
