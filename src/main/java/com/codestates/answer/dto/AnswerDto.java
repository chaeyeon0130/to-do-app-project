package com.codestates.answer.dto;

import com.codestates.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AnswerDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @Positive
        @NotNull
        private Long adminId;

        @Positive
        @NotNull
        private Long questionId;

        @NotNull(message = "답변 내용은 필수입니다.")
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private long answerId;
        @NotNull
        private Long adminId;
        @NotSpace(message = "답변 내용은 공백이 아니어야 합니다")
        private String content;

        public void setAnswerId(long answerId) {
            this.answerId = answerId;
        }
    }
}
