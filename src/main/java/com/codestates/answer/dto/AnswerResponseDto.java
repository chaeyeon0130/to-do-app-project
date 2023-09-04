package com.codestates.answer.dto;

import com.codestates.administrator.entity.Admin;
import com.codestates.answer.entity.Answer;
import com.codestates.question.entity.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnswerResponseDto {
    private long answerId;
//    @Setter(AccessLevel.NONE)
//    private String question;
    private String content;
    @Setter(AccessLevel.NONE)
    private long adminId;
    private Answer.AnswerSetting answerSetting;

//    public void setQuestion(Question question) {
//        this.question = question.getTitle();
//    }
    public void setAdmin(Admin admin) {
        this.adminId = admin.getAdminId();
    }
}
