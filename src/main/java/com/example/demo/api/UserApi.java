package com.example.demo.api;

import com.example.demo.dto.SimpleResponse;
import com.example.demo.dto.UpdateUserRequest;
import com.example.demo.dto.UserProfile;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping     // own profile
    public UserProfile getOwnProfile(){
        return  userService.getOwnUserProfile();
    }

    @GetMapping("/{userId}")
    public UserProfile getUserProfile(@PathVariable Long userId){
        return userService.getUserProfile(userId);
    }

    @PutMapping("/update")
    public SimpleResponse updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        return userService.updateUser(updateUserRequest);
    }

    @DeleteMapping
    public SimpleResponse deleteUser(){
        return userService.deleteUser();
    }

}
