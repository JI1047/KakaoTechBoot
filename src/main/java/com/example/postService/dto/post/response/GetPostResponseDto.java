package com.example.postService.dto.post.response;

import com.example.postService.dto.comment.GetCommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPostResponseDto {
    private String title;

    private String text;

    private String postImage;

    private LocalDateTime createdAt;

    private Integer likeCount;

    private Integer commentCount;

    private Integer lookCount;

    private String nickname; //사용자 닉네임

    private String profileImage;//사용자 프로필 이미지

    private List<GetCommentResponseDto> getCommentResponseDto;

}
//제목 내용 이미지 생성일자 (Post)
//좋아요 수, 조회 수, 댓글 수,
//댓글 목록(List) - 댓글 작성자,댓글 작성자 프로필 이미지, 댓글 생성일자, 댓글 내용