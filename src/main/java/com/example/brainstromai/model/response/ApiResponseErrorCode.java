package com.example.brainstromai.model.response;

public enum ApiResponseErrorCode {
    SUCCESS(0),
    LoginFail(1),
    SignUpFail(2),
    CreateProjectFail(3),
    InvalidUser(4),
    RuntimeException(5);

    private final int code;

    ApiResponseErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
