package com.example.securityproject.user.exception;

/**
 * 대상 사용자를 찾을 수 없을 때 발생하는 Exception
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("대상 사용자를 찾을 수 없습니다.");
    }
}
