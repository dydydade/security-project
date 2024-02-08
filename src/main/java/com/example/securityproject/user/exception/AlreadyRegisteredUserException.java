package com.example.securityproject.user.exception;

/**
 * 이미 등록된 유저를 재등록하려고 할 때 발생하는 Exception
 */
public class AlreadyRegisteredUserException extends RuntimeException {

    public AlreadyRegisteredUserException(String message) {
        super(message);
    }

    public AlreadyRegisteredUserException() {
        super("기존에 이미 등록된 유저입니다.");
    }
}
