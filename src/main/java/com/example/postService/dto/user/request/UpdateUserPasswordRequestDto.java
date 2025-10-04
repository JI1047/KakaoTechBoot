package com.example.postService.dto.user.request;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserPasswordRequestDto {

    @Description("넌 미쳤어")
    private String newPassword;

    private String confirmPassword;


}
