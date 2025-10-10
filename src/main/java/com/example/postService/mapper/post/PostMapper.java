package com.example.postService.mapper.post;

import com.example.postService.dto.comment.response.GetCommentResponseDto;
import com.example.postService.dto.post.response.GetPostListResponseDto;
import com.example.postService.dto.post.response.GetPostResponseDto;
import com.example.postService.dto.post.resquest.CreatePostRequestDto;
import com.example.postService.entity.post.Post;
import com.example.postService.entity.post.PostContent;
import com.example.postService.entity.post.PostView;
import com.example.postService.entity.user.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {


    PostContent postContentDtoToPostContent(CreatePostRequestDto dto);

    Post toPost(CreatePostRequestDto dto, PostView postView, UserProfile userProfile, PostContent postContent);

    @Mapping(source = "postView.likeCount", target = "likeCount")
    @Mapping(source = "postView.commentCount", target = "commentCount")
    @Mapping(source = "postView.lookCount", target = "lookCount")
    GetPostListResponseDto toGetPostListResponseDto(Post post, PostView postView, UserProfile userProfile);

    GetPostResponseDto toGetPostResponseDto(Post post, PostContent postContent, PostView postView, UserProfile userProfile, List<GetCommentResponseDto> responseDtoList);
}
