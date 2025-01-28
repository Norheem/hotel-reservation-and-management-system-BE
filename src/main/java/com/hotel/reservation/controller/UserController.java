package com.hotel.reservation.controller;


import com.hotel.reservation.entity.enums.Gender;
import com.hotel.reservation.payload.request.RegistrationRequest;
import com.hotel.reservation.payload.response.AuthResponse;
import com.hotel.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegistrationRequest request,
            @RequestParam Gender gender) {
        return ResponseEntity.ok(userService.register(request, gender));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam token) {
        return ResponseEntity.ok(userService.verifyUser(token));
    }
}
