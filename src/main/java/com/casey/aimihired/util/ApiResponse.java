package com.casey.aimihired.util;

public class ApiResponse {
    private String response;
    private boolean success;

    public ApiResponse(String response, boolean success) {
        this.response = response;
        this.success = success;
    }
}
