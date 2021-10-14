package ru.afso.projectzero.utils;

public class ErrorResponse<T> extends ApiResponse<T>{

    public ErrorResponse(T payload) {
        this.payload = payload;
        this.success = false;
    }

}
