package com.example.demo.service;

import com.example.demo.dto.SimpleResponse;
import com.example.demo.dto.UserInfoResponse;
import com.example.demo.entities.UserInfo;

public interface UserInfoService {

    SimpleResponse updateUserInfo(String imageUrl);

    UserInfoResponse getOwnInfo();

    SimpleResponse deleteImage();

}
