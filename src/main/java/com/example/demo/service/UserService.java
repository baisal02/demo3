package com.example.demo.service;

import com.example.demo.dto.*;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserService {
    //auth
    AuthResponse registerInApp(UserRegisterRequest userRegister);
    AuthResponse login(LoginRequest loginRequest);

    //
    UserProfile getOwnUserProfile();// (using ContextHolder) to get profile of authUser
    UserProfile getUserProfile(Long id); // to get anyones profile

    //
    SimpleResponse updateUser(UpdateUserRequest updateUserRequest);

    //
    SimpleResponse deleteUser();
}
