package com.codestates.question.dto;

import com.codestates.answer.dto.AnswerResponseDto;
import com.codestates.member.entity.Member;
import com.codestates.question.entity.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionResponseDto {
    private long questionId;
    @Setter(AccessLevel.NONE)
    private long memberId;
    private String title;
    private String content;
    private Question.QuestionSetting questionSetting;
    private Question.QuesitonStatus quesitonStatus;
    private List<AnswerResponseDto> answers;
    private LocalDateTime createdAt;

    public void setMember(Member member) {
        this.memberId = member.getMemberId();
    }
}
