package com.codestates.answer.mapper;

import com.codestates.administrator.entity.Admin;
import com.codestates.answer.dto.AnswerDto;
import com.codestates.answer.dto.AnswerResponseDto;
import com.codestates.answer.entity.Answer;
import com.codestates.question.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    default Answer answerPostDtoToAnswer(AnswerDto.Post answerPostDto) {
        Answer answer = new Answer();
        Admin admin = new Admin();
        Question question = new Question();

        admin.setAdminId(answerPostDto.getAdminId());
        question.setQuestionId(answerPostDto.getQuestionId());

        answer.setContent(answerPostDto.getContent());
        answer.setQuestion(question);
        answer.setAdmin(admin);

        return answer;
    }

    default Answer answerPatchDtoToAnswer(AnswerDto.Patch answerPatchDto) {
        Answer answer = new Answer();
        Admin admin = new Admin();

        admin.setAdminId(answerPatchDto.getAdminId());

        answer.setAnswerId(answerPatchDto.getAnswerId());
        answer.setAdmin(admin);
        answer.setContent(answerPatchDto.getContent());

        return answer;
    }

    AnswerResponseDto answerToAnswerResponseDto(Answer answer);
}
