package com.example.postService.mapper.user;

import com.example.postService.dto.user.request.CreateUserRequestDto;
import com.example.postService.dto.user.response.CreateUserResponseDto;
import com.example.postService.dto.user.response.GetUserResponseDto;
import com.example.postService.dto.user.session.SessionUser;
import com.example.postService.entity.user.User;
import com.example.postService.entity.user.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper (componentModel = "spring")
public interface UserMapper {

    UserProfile createUserRequestDtoToUserProfile(CreateUserRequestDto dto);

    User createUserRequestDto(CreateUserRequestDto dto, UserProfile userProfile);

    CreateUserResponseDto userToCreateUserResponseDto(User user);


    GetUserResponseDto userToUGetUserResponseDto(User user);

    SessionUser userToSessionUser(User user);

}
