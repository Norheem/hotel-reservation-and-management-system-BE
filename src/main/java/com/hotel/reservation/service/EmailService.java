package com.hotel.reservation.service;

import com.hotel.reservation.payload.response.EmailDetails;

public interface EmailService {

    void sendEmailToken(EmailDetails emailDetails);
}
