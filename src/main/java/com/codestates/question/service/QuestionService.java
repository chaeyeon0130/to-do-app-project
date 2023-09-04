package com.codestates.question.service;

import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.entity.Member;
import com.codestates.member.service.MemberService;
import com.codestates.question.entity.Question;
import com.codestates.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.codestates.question.entity.Question.QuesitonStatus.QUESTION_ANSWERED;
import static com.codestates.question.entity.Question.QuesitonStatus.QUESTION_DELETE;
import static com.codestates.question.entity.Question.QuestionSetting.QUESTION_SECRET;

@Service
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;

    private final MemberService memberService;

    public QuestionService(QuestionRepository questionRepository, MemberService memberService) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
    }

    public Question createQuestion(Question question) {
        verifyQuesiton(question);
        Question savedQuestion = saveQuestion(question);

        return savedQuestion;
    }

    public Question updateQuestion(Question question) {
        // 존재하는 quesiton인지 확인
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());

        // 질문 수정이 가능한지 확인
        checkUpdatePossibility(question, findQuestion);

        // 질문 수정(일일히?)
        Optional.ofNullable(question.getTitle())
                .ifPresent(questionTitle -> findQuestion.setTitle(questionTitle));
        Optional.ofNullable(question.getContent())
                .ifPresent(questionContent -> findQuestion.setContent(questionContent));
        Optional.ofNullable(question.getQuestionSetting())
                .ifPresent(questionSetting -> findQuestion.setQuestionSetting(questionSetting));

        // 수정된 질문 객체 .레파지토리에 저장
        return saveQuestion(findQuestion);
    }

    public Question findQuestion(long questionId, String email) {
        Question verifiedQuestion = findVerifiedQuestion(questionId);

        // 조회가 가능한지 확인
        checkFindPossibility(verifiedQuestion, email);

        return verifiedQuestion;
    }

    public void deleteQuestion(long questionId, String email) {
        Question findQuestion = findVerifiedQuestion(questionId);

        // 삭제가 가능한지 확인
        checkDeletePossibility(findQuestion, email);

        findQuestion.setQuesitonStatus(QUESTION_DELETE);
        questionRepository.save(findQuestion);
    }

    public Page<Question> findQuestions(int page, int size, String condition) {
        switch (condition.toLowerCase()) {
            case "newest":
                return questionRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));
            case "oldest":
                return questionRepository.findAll(PageRequest.of(page, size));
            default:
                throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
        }
    }

    private void checkDeletePossibility(Question question, String email) {
        if (question.getQuesitonStatus() == QUESTION_DELETE) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_DELETED_QUESTION);
        }

        if (!email.equals(question.getMember().getEmail())) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_DELETE_QUESTION);
        }
    }

    private void checkFindPossibility(Question question, String email) {
        if (question.getQuesitonStatus() == QUESTION_DELETE) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_FIND_QUESTION);
        }

        if (question.getQuestionSetting() == QUESTION_SECRET) {
            if (email.equals("admin@gmail.com")) {
                return;
            }
            if (email.equals(question.getMember().getEmail())) {
                return;
            }
        }
        else {
            return;
        }

        throw new BusinessLogicException(ExceptionCode.CANNOT_FIND_QUESTION);
    }

    private void checkUpdatePossibility(Question changedQuestion, Question originalQuestion) {
        // 답변이 완료된 질문은 수정할 수 없음
        if (changedQuestion.getQuesitonStatus() == QUESTION_ANSWERED) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_QUESTION);
        }

        // 질문을 등록한 회원이 수정하는게 맞는지 확인
        if (changedQuestion.getMember().getMemberId() != originalQuestion.getMember().getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_QUESTION);
        }
    }

    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESITON_NOT_FOUND));

        return findQuestion;
    }

    private void verifyQuesiton(Question question) {
        // 질문한 회원이 존재하는지 확인
        Member member = memberService.findVerifiedMember(question.getMember().getMemberId());

        question.setMember(member);
    }
    
    private Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }
}
