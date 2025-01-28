package com.hotel.reservation.service.impl;

import com.hotel.reservation.entity.TokenVerification;
import com.hotel.reservation.entity.User;
import com.hotel.reservation.service.TokenVerificationService;

public class TokenVerificationServiceImpl implements TokenVerificationService {
    @Override
    public String generateVerificationToken(User user) {
        return "";
    }

    @Override
    public TokenVerification validateToken(String token) {
        return null;
    }

    @Override
    public void deleteToken(TokenVerification token) {

    }
}
