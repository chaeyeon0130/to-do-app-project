package com.codestates.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    COFFEE_NOT_FOUND(404, "Coffee not found"),
    COFFEE_CODE_EXISTS(409, "Coffee Code exists"),
    ORDER_NOT_FOUND(404, "Order not found"),
    CANNOT_CHANGE_ORDER(403, "Order can not change"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    QUESITON_NOT_FOUND(404, "Question not found"),
    CANNOT_CHANGE_QUESTION(403, "Question can not change"),
    CANNOT_FIND_QUESTION(403, "Question can not find"),
    CANNOT_DELETE_QUESTION(403, "Question can not delete"),
    ALREADY_DELETED_QUESTION(410, "Question is already deleted"),
    ADMIN_EXISTS(409, "Admin exists"),
    ADMIN_NOT_FOUND(404, "Admin not found"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    CANNOT_CHANGE_ANSWER(403, "Answer can not change"),
    CANNOT_DELETE_ANSWER(403, "Answer can not delete");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
