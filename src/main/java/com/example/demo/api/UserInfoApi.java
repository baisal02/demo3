package com.example.demo.api;

import com.example.demo.dto.SimpleResponse;
import com.example.demo.dto.UserInfoResponse;
import com.example.demo.entities.UserInfo;
import com.example.demo.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userinfo")
public class UserInfoApi {
    private final UserInfoService userInfoService;

    public UserInfoApi(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PutMapping("/{imageUrl}")
    public SimpleResponse updateUserInfo(@PathVariable String imageUrl) {
        return userInfoService.updateUserInfo(imageUrl);
    }

    @GetMapping("/getOwnInfo")
    public UserInfoResponse getUserInfo() {
        return userInfoService.getOwnInfo();
    }

    @DeleteMapping("/deleteProfileImage")
    public SimpleResponse deleteImage() {
        return userInfoService.deleteImage();
    }
}
