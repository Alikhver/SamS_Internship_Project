package com.alikhver.web.dto.organisation.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class CreateOrganisationRequest {
    @NotBlank
    @Size(min = 2, max = 30)
    private String name;
    @NotBlank
    @Size(min = 30)
    private String description;
    @NotBlank
    private String address;
    @NotBlank
    private String redactorLogin;
    @NotBlank
    private String redactorPassword;
}
