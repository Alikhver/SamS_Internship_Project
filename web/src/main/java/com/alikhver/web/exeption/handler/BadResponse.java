package com.alikhver.web.exeption.handler;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class BadResponse {

    private int status;

    private String info;

}
