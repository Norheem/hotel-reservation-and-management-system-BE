package com.hotel.reservation.service;

import com.hotel.reservation.entity.enums.Gender;
import com.hotel.reservation.payload.request.RegistrationRequest;
import com.hotel.reservation.payload.response.AuthResponse;

public interface UserService {

    AuthResponse register(RegistrationRequest request, Gender gender);

    String verifyUser(String token);
}
