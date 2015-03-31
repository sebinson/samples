package net.sebinson.sample.web.common.beans;

import java.io.Serializable;

public class ResponseJson implements Serializable {

    private static final long serialVersionUID = 6071418096552334183L;

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

    public ResponseJson(boolean success) {
        this.success = success;
        this.msg = "操作失败";
        if (success) {
            this.msg = "操作成功";
        }
    }

    public ResponseJson(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public ResponseJson(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseJson body() {
        return new ResponseJson(true);
    }

    public static ResponseJson body(boolean success) {
        return new ResponseJson(success);
    }

    public static ResponseJson body(boolean success, String msg) {
        return new ResponseJson(success, msg);
    }

    public static ResponseJson body(boolean success, String msg, Object data) {
        return new ResponseJson(success, msg, data);
    }

    public static ResponseJson body(boolean success, Object data) {
        return new ResponseJson(success, null, data);
    }
}
