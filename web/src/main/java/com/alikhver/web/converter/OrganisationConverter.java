package com.alikhver.web.converter;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrganisationConverter {
    private final WorkerConverter workerConverter;
    private final UtilityConverter utilityConverter;

    public GetOrganisationResponse mapToGetOrganisationResponse(Organisation organisation) {
        return GetOrganisationResponse.builder()
                .id(organisation.getId())
                .name(organisation.getName())
                .description(organisation.getDescription())
                .address(organisation.getAddress())
                .utilities(organisation.getUtilities().stream()
                        .map(utilityConverter::mapToGetUtilityResponse)
                        .collect(Collectors.toList()))
                .workers(organisation.getWorkers().stream()
                        .map(workerConverter::mapToGetWorkerResponse).
                        collect(Collectors.toList()))
                .isActive(organisation.isActive())
                .build();
    }

    public List<GetOrganisationResponse> mapToListOfGetOrganisationResponse(List<Organisation> organisations) {
        return organisations.stream()
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
                .id(organisation.getId())
                .name(organisation.getName())
                .description(organisation.getDescription())
                .address(organisation.getAddress())
                .build();
    }
}
