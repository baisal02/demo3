package com.example.demo.api;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class RegisterLoginApi {
    private final UserService userService;

    @Autowired
    public RegisterLoginApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AuthResponse registerInApp(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.registerInApp(userRegisterRequest);
    }

    @PostMapping("/login")
    public AuthResponse loginInApp(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

}
