package com.codestates.question.entity;

import com.codestates.answer.entity.Answer;
import com.codestates.audit.Auditable;
import com.codestates.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.codestates.answer.entity.Answer.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 50, nullable = false)
    private QuesitonStatus quesitonStatus = QuesitonStatus.QUESTION_REGISTRATION;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 50, nullable = false)
    private QuestionSetting questionSetting = QuestionSetting.QUESTION_PUBLIC;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    public enum QuesitonStatus {
        QUESTION_REGISTRATION("질문등록"),
        QUESTION_ANSWERED("답변완료"),
        QUESTION_DELETE("질문삭제");

        @Getter
        private String status;

        QuesitonStatus(String status) {
            this.status = status;
        }
    }

    public enum QuestionSetting {
        QUESTION_PUBLIC("공개글"),
        QUESTION_SECRET("비밀글");

        @Getter
        private String status;

        QuestionSetting(String status) {
            this.status = status;
        }
    }

    public void setQuestionSetting(QuestionSetting setting) {
        this.questionSetting = setting;
        for (Answer answer : answers) {
            answer.setAnswerSetting(setting.getStatus().equals("공개글") ? AnswerSetting.ANSWER_PUBLIC : AnswerSetting.ANSWER_SECRET);
        }
    }
}
