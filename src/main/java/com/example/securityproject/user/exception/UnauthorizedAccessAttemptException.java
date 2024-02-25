package com.example.securityproject.user.exception;

/**
 * 인가되지 않은 사용자가 특정 동작을 수행하려고 할 때 발생하는 Exception
 */
public class UnauthorizedAccessAttemptException extends RuntimeException {
    public UnauthorizedAccessAttemptException(String message) {
        super(message);
    }

    public UnauthorizedAccessAttemptException() {
        super("대상 사용자에게 인가되지 않은 접근입니다.");
    }
}
