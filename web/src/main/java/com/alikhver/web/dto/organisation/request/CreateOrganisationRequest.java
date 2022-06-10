package com.alikhver.web.dto.organisation.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class CreateOrganisationRequest {
    @NotBlank
    @Size(min = 2, max = 30)
    private String name;
    @NotBlank
    @Size(min = 30, max = 120)
    private String description;
    @NotBlank
    @Size(min = 5, max = 45)
    private String address;
    @NotBlank
    @Size(min = 7, max = 30)
    private String redactorLogin;
    @NotBlank
    @Size(min = 7, max = 30)
    private String redactorPassword;
}
