package com.example.postService.config;

import com.example.postService.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;//로그인 체크 인터셉터

    /**
     * InterceptorRegistry을 통해 전역 인터셉터 등록
     * 기본적으로 모든 API 요청에 세션 검증 적용
     * 인증 필요 없는 경로는 예외 설정
     */
    // login 을 따로 빼면 /api/auth/** 로 해결됨
    // /api/posts/**의 GET 만 허용하면 됨
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/api/**")//모든 api가 세션 필요로 설정
                .excludePathPatterns("/api/users/login",
                        "/api/users/signup",
                        "/api/posts/list",
                        "/api/posts/{postId}"
                );//로그인,회원가입,게시물 목록 조회, 게시물 상세 조회는 세션 없이 진행되도록 예외 처리
    }
}
