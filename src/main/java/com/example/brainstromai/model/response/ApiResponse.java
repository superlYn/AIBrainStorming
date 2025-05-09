package com.example.brainstromai.model.response;


import java.time.LocalDateTime;


public class ApiResponse<T> {


    private final int code;
    private final String message;
    private final T data;
    private final String timeStamp = LocalDateTime.now().toString();

    public ApiResponse(T data, String message, int code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(data, "success", ApiResponseErrorCode.SUCCESS.getCode());
    }

    public static <T> ApiResponse<T> fail(T data, ApiResponseErrorCode code) {
        return new ApiResponse<T>(data, "fail", code.getCode());
    }
}

