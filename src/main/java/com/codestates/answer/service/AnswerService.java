package com.codestates.answer.service;

import com.codestates.administrator.entity.Admin;
import com.codestates.administrator.service.AdminService;
import com.codestates.answer.entity.Answer;
import com.codestates.answer.repository.AnswerRepository;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.question.entity.Question;
import com.codestates.question.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final AdminService adminService;
    private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, AdminService adminService, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.adminService = adminService;
        this.questionService = questionService;
    }

    public Answer createAnswer(Answer answer) {
        verifyAnswer(answer);
        Answer savedAnswer = saveAnswer(answer);

        return savedAnswer;
    }

    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());

        checkUpdatePossibility(answer, findAnswer);

        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));

        return saveAnswer(findAnswer);
    }

    public void deleteAnswer(long answerId, long adminId) {
        Answer findAnswer = findVerifiedAnswer(answerId);

        checkDeletePossibility(findAnswer, adminId);

        answerRepository.delete(findAnswer);
    }

    private void checkDeletePossibility(Answer answer, long adminId) {
        if (answer.getAdmin().getAdminId() != adminId) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_DELETE_ANSWER);
        }
    }

    private void checkUpdatePossibility(Answer changedAnswer, Answer originalAnswer) {
        if (changedAnswer.getAdmin().getAdminId() != originalAnswer.getAdmin().getAdminId()) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ANSWER);
        }
    }

    private Answer findVerifiedAnswer(long answerId) {
        Optional<Answer> optionalAnswer =
                answerRepository.findById(answerId);
        Answer findAnswer =
                optionalAnswer.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        return findAnswer;
    }

    private void verifyAnswer(Answer answer) {
        // 질문한 관리자가 존재하는지 확인
        Admin admin = adminService.findVerifiedAdmin(answer.getAdmin().getAdminId());
        // 관리자 setting 하고
        answer.setAdmin(admin);

        // 답변한 질무닝 존재하는지 확인
        Question question = questionService.findVerifiedQuestion(answer.getQuestion().getQuestionId());
        question.setQuesitonStatus(Question.QuesitonStatus.QUESTION_ANSWERED);
        // 질문 setting 하고
        answer.setQuestion(question);
    }

    private Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }
}
