package com.hotel.reservation.service.impl;

import com.hotel.reservation.entity.TokenVerification;
import com.hotel.reservation.entity.User;
import com.hotel.reservation.entity.enums.Gender;
import com.hotel.reservation.exception.customExceptions.EmailAlreadyExistsException;
import com.hotel.reservation.exception.customExceptions.InvalidPasswordException;
import com.hotel.reservation.exception.customExceptions.InvalidTokenException;
import com.hotel.reservation.payload.request.LoginRequest;
import com.hotel.reservation.payload.request.RegistrationRequest;
import com.hotel.reservation.payload.response.AuthResponse;
import com.hotel.reservation.payload.response.EmailDetails;
import com.hotel.reservation.payload.response.LoginResponse;
import com.hotel.reservation.payload.response.RegistrationInfo;
import com.hotel.reservation.repository.UserRepository;
import com.hotel.reservation.service.EmailService;
import com.hotel.reservation.service.TokenVerificationService;
import com.hotel.reservation.service.UserService;
import com.hotel.reservation.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.hotel.reservation.entity.enums.Role.CUSTOMER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenVerificationService tokenVerificationService;

    private final EmailService emailService;


    @Override
    public AuthResponse register(RegistrationRequest request, Gender gender) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists, kindly log into your account");
        }

        if (!isValidPassword(request.getPassword())) {
            throw new InvalidPasswordException("Password must be at least 8 characters long and contain at least one special character.");
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

        String token = tokenVerificationService.generateVerificationToken(savedNewUser);

        String verificationUrl = "http://localhost:8080/api/v1/auth/verify?token=" + token;

        //send email alert
        String emailMessageBody = String.format(
                "Dear %s,\n" +
                        "\n" +
                        "Welcome to Roomify, your ultimate hotel reservation and management platform! To complete your registration and activate your account, please verify your email address by clicking the link below:\n" +
                        "\n" +
                        "Verification Link: %s\n" +
                        "\n" +
                        "If the link doesn’t work, copy and paste the URL into your browser.\n" +
                        "\n" +
                        "This verification step ensures the security of your account.\n" +
                        "\n" +
                        "If you did not sign up for Roomify, please ignore this email.\n" +
                        "\n" +
                        "For any assistance, feel free to contact us at support@roomify.com.\n" +
                        "\n" +
                        "Thank you for choosing Roomify!\n" +
                        "\n" +
                        "Best regards,\n" +
                        "The Roomify Team\n",
                savedNewUser.getFirstName(),
                verificationUrl
        );


        EmailDetails sendTokenForRegistration = EmailDetails.builder()
                .recipient(request.getEmail())
                .subject("Verify Your Roomify Account")
                .messageBody(emailMessageBody)
                .build();
        emailService.sendEmailToken(sendTokenForRegistration);


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

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.matches(".*[!@#$%^&*()].*");
    }

    @Override
    public String verifyUser(String token) {
        TokenVerification verificationToken = tokenVerificationService.validateToken(token);


        if (verificationToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token expired. Please register again.");
        }

        User user = verificationToken.getUser();
        user.setActive(true);
        userRepository.save(user);

        tokenVerificationService.deleteToken(verificationToken);

        return "User account successfully verified and activated.";
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }
}
