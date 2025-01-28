package com.hotel.reservation.service.impl;

import com.hotel.reservation.entity.TokenVerification;
import com.hotel.reservation.entity.User;
import com.hotel.reservation.entity.enums.Gender;
import com.hotel.reservation.payload.request.RegistrationRequest;
import com.hotel.reservation.payload.response.AuthResponse;
import com.hotel.reservation.payload.response.RegistrationInfo;
import com.hotel.reservation.repository.UserRepository;
import com.hotel.reservation.service.UserService;
import com.hotel.reservation.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hotel.reservation.entity.enums.Role.CUSTOMER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public AuthResponse register(RegistrationRequest request, Gender gender) {

        if (userRepository.existsByEmail(request.getEmail())) {
            AuthResponse response = AuthResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .registrationInfo(null)
                    .build();

            return response;
        }

        // register new customer
        User newUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .address(request.getAddress())
                .gender(gender)
                .role(CUSTOMER)
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .isActive(false)
                .resetToken(null)
                .resetTokenExpiry(null)
                .build();
        User savedNewUser = userRepository.save(newUser);

        //send email alert


        return AuthResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .registrationInfo(RegistrationInfo.builder()
                        .firstName(savedNewUser.getFirstName())
                        .lastName(savedNewUser.getLastName())
                        .email(savedNewUser.getEmail())
                        .phoneNumber(savedNewUser.getPhoneNumber())
                        .build())
                .token(null)
                .build();
    }

    @Override
    public String verifyUser(String token) {
        TokenVerification tokenVerification
    }
}
