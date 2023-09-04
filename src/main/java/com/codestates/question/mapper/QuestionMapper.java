package com.codestates.question.mapper;

import com.codestates.answer.dto.AnswerResponseDto;
import com.codestates.member.entity.Member;
import com.codestates.question.dto.QuestionPatchDto;
import com.codestates.question.dto.QuestionPostDto;
import com.codestates.question.dto.QuestionResponseDto;
import com.codestates.question.entity.Question;
import com.codestates.answer.entity.Answer;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel ="spring")
public interface QuestionMapper {
    default Question questionPostDtoToQuestion(QuestionPostDto questionPostDto) {
        Question question = new Question();
        Member member = new Member();
        member.setMemberId(questionPostDto.getMemberId());

        question.setMember(member);
        question.setTitle(questionPostDto.getTitle());
        question.setContent(questionPostDto.getContent());

        if (questionPostDto.getQuestionSetting() != null) {
            question.setQuestionSetting(questionPostDto.getQuestionSetting());
        }

        return question;
    }

    default Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto) {
        System.out.println("========= patchdto mapping 시작 =========");
        Question question = new Question();
        Member member = new Member();
        member.setMemberId(questionPatchDto.getMemberId());

        question.setMember(member);
        question.setQuestionId(questionPatchDto.getQuestionId());
        question.setTitle(questionPatchDto.getTitle());
        question.setContent(questionPatchDto.getContent());
        question.setQuestionSetting(questionPatchDto.getQuestionSetting());

        return question;
    }

    default QuestionResponseDto questionToQuestionResponseDto(Question question) {
        if (question.getQuesitonStatus() == Question.QuesitonStatus.QUESTION_DELETE) {
            return new QuestionResponseDto();
        }

        List<Answer> answers = question.getAnswers();

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setMember(question.getMember());
        questionResponseDto.setTitle(question.getTitle());
        questionResponseDto.setContent(question.getContent());
        questionResponseDto.setQuestionSetting(question.getQuestionSetting());
        questionResponseDto.setQuesitonStatus(question.getQuesitonStatus());
        questionResponseDto.setAnswers(
                answersToAnswerResponseDtos(answers)
        );
        questionResponseDto.setCreatedAt(question.getCreatedAt());

        return questionResponseDto;
    }

    default List<AnswerResponseDto> answersToAnswerResponseDtos(
            List<Answer> answers) {
        return answers.stream()
                .map(answer -> {
                    AnswerResponseDto answerResponseDto = new AnswerResponseDto();
                    answerResponseDto.setAnswerId(answer.getAnswerId());
                    answerResponseDto.setContent(answer.getContent());
                    answerResponseDto.setAdmin(answer.getAdmin());
                    answerResponseDto.setAnswerSetting(answer.getAnswerSetting());

                    return answerResponseDto;
                })
                .collect(Collectors.toList());
    }

    default List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions) {
        if (questions == null) {
            return null;
        }

        List<Question> retrievePossible = questions
                .stream()
                .filter(question -> question.getQuesitonStatus() != Question.QuesitonStatus.QUESTION_DELETE)
                .collect(Collectors.toList());

        List<QuestionResponseDto> list = new ArrayList<>(retrievePossible.size());

        for (Question question : retrievePossible) {
            list.add(questionToQuestionResponseDto(question));
        }

        return list;
    }
}
