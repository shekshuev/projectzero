package ru.afso.projectzero.utils;

public abstract class ApiResponse<T> {

    protected boolean success;

    protected T payload;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
