package com.saarit.temple_management.templemanagement.model;

public class SuccessOrFailure {

    int success;
    String msg;

    public SuccessOrFailure(int success, String msg){
        this.success = success;
        this.msg = msg;

    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
