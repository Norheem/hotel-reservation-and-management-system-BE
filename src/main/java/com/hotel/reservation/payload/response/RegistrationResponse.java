package com.hotel.reservation.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponse {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String token;
}
