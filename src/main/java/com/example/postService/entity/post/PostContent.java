package com.example.postService.entity.post;

import com.example.postService.dto.post.resquest.UpdatePostRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_content")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String text;


    private String postImage;

    public void updatePostContent(UpdatePostRequestDto updatePostRequestDto) {
        this.text = updatePostRequestDto.getText();
        this.postImage = updatePostRequestDto.getPostImage();
    }
}
//게시글 목록 조회가 사용자들이 가장 많이 사용될 페이지로 예상해서
//무게가 있는 데이터 text,postImage를 Post에 같이 놔둘 경우 조회가 느려질 수 있기 때문에 따로 분리
