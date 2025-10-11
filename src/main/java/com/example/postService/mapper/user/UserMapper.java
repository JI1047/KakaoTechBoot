package com.example.postService.mapper.user;

import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import com.example.postService.dto.user.session.UserSession;
import com.example.postService.entity.user.User;
import com.example.postService.entity.user.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface UserMapper {


    UserProfile createUserRequestDtoToUserProfile(CreateUserRequestDto dto);

    User createUserRequestDto(CreateUserRequestDto dto, UserProfile userProfile);

    @Mapping(source = "userProfile.nickname", target = "nickname")
    @Mapping(source = "userProfile.profileImage", target = "profileImage")
    CreateUserResponseDto userToCreateUserResponseDto(User user);

    @Mapping(source = "userProfile.nickname", target = "nickname")
    @Mapping(source = "userProfile.profileImage", target = "profileImage")
    GetUserResponseDto userToUGetUserResponseDto(User user);

    @Mapping(source = "userProfile.id", target = "userProfileId")
    @Mapping(source = "userProfile.nickname", target = "nickname")
    @Mapping(source = "userProfile.profileImage",target = "profileImage")
    UserSession userProfileToSessionUser(UserProfile userProfile);

}
