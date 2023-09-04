package com.codestates.question.dto;

import com.codestates.question.entity.Question;
import com.codestates.question.entity.Question.QuestionSetting;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class QuestionPostDto {
    @Positive
    @NotNull(message = "user-id는 필수입니다.")
    private Long memberId;

    @NotNull(message = "질문 제목은 필수입니다.")
    private String title;

    @NotNull(message = "질문 내용은 필수입니다.")
    private String content;

    private QuestionSetting questionSetting;
}
