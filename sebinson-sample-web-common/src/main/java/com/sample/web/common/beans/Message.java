package com.sample.web.common.beans;

public class Message {

    private boolean success;

    private String msg;

    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Message(boolean success) {
        this.success = success;
        this.msg = "操作失败";
        if (success) {
            this.msg = "操作成功";
        }
    }

    public Message(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Message(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static Message getMessage(boolean success) {
        return new Message(success);
    }

    public static Message getMessage(boolean success, String msg) {
        return new Message(success, msg);
    }

    public static Message getMessage(boolean success, String msg, Object data) {
        return new Message(success, msg, data);
    }
}
