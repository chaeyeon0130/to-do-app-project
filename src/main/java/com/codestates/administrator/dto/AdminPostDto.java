package com.codestates.administrator.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AdminPostDto {
    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    private String name;
}
