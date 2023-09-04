package com.codestates.answer.entity;

import com.codestates.administrator.entity.Admin;
import com.codestates.audit.Auditable;
import com.codestates.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.codestates.question.entity.Question;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Answer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

//    public void setQuestion(Question question) {
//        this.question = question;
//        if (!question.getAnswers().contains(this)) {
//            question.getAnswers().add(this);
//        }
//    }

    @ManyToOne
    @JoinColumn(name = "ADMIN_ID")
    private Admin admin;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 50, nullable = false)
    private AnswerSetting answerSetting = AnswerSetting.ANSWER_PUBLIC;

    public enum AnswerSetting {
        ANSWER_PUBLIC("공개댓글"),
        ANSWER_SECRET("비밀댓글");

        @Getter
        private String status;

        AnswerSetting(String status) {
            this.status = status;
        }
    }
}
