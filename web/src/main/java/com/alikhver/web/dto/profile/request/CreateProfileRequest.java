package com.alikhver.web.dto.profile.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
public class CreateProfileRequest {
    @NotBlank
    @Size(max = 45)
    private String firstName;
    @NotBlank
    @Size(max = 45)
    private String lastName;
    @NotBlank
    //TODO move to properties
//    +55 11 99999-5555 Brazil
//
//+593 7 282-3889 Ecuador
//
//+1 284 852 5500 BVI
//
//+1 345 9490088 Grand Cayman
//
//+32 2 702-9200 Belgium
//
//+65 6511 9266 Asia Pacific
//
//+86 21 2230 1000 Shanghai
//
//+9124 4723300 India
//
//+821012345678 South Korea
    @Pattern(regexp = "\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?",
            message = "Not correct phone number")
    private String phoneNumber;
    @Email
    @Size(max = 45)
    @NotBlank
    private String email;
    @Positive
    @NotNull
    private Long userId;
}
