package com.codestates.question.dto;

import com.codestates.question.entity.Question;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class QuestionPatchDto {
    private Long questionId;
    @NotNull(message = "member-id는 필수입니다.")
    private Long memberId;
    private String title;
    private String content;
    private Question.QuestionSetting questionSetting;

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
}
