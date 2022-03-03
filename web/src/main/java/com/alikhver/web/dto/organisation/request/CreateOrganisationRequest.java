package com.alikhver.web.dto.organisation.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateOrganisationRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String address;
    @NotBlank
    private String redactorLogin;
    @NotBlank
    private String redactorPassword;
}
