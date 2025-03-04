package com.example.demo.service.impl;

import com.example.demo.dto.SimpleResponse;
import com.example.demo.dto.UserInfoResponse;
import com.example.demo.entities.User;
import com.example.demo.entities.UserInfo;
import com.example.demo.repositories.UserInfoRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.service.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepo userInfoRepo;
    private final UserRepo userRepo;

    public UserInfoServiceImpl(UserInfoRepo userInfoRepo, UserRepo userRepo) {
        this.userInfoRepo = userInfoRepo;
        this.userRepo = userRepo;
    }

    @Override
    public SimpleResponse updateUserInfo(String imageUrl) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(currentUserEmail).orElseThrow(()->new UsernameNotFoundException("User not found"));
        if(user.getUserInfo()!=null){
            UserInfo userInfo = user.getUserInfo();
            userInfo.setImageUrl(imageUrl);
            userRepo.save(user);
            return new SimpleResponse("image updated", HttpStatus.OK);
        }
        return new SimpleResponse("something went wrong", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserInfoResponse getOwnInfo() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(currentEmail)//current user
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        if(user.getUserInfo()!=null) {
            UserInfoResponse userInfoResponse = new UserInfoResponse();
            UserInfo userInfo = user.getUserInfo();

            userInfoResponse.setUserId(user.getId());
            userInfoResponse.setUserInfoId(userInfo.getId());
            userInfoResponse.setFullName(userInfo.getFullName());
            userInfoResponse.setBiography(userInfo.getBiography());
            userInfoResponse.setImageUrl(user.getUserInfo().getImageUrl());

            return userInfoResponse;
        }
        return null;
    }

    @Override
    public SimpleResponse deleteImage() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findUserByEmail(currentUserEmail).orElseThrow(()->new UsernameNotFoundException("User not found"));

        if(user.getUserInfo()!=null){
            user.getUserInfo().setImageUrl(null);
        }

        userRepo.save(user);
        return new SimpleResponse("sucessfully deleted", HttpStatus.OK);
    }

}
