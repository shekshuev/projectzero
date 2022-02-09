package ru.afso.projectzero.utils;

public class SuccessResponse<T> extends BaseResponse<T> {

    public SuccessResponse(T payload) {
        this.payload = payload;
        this.success = true;
    }
}
