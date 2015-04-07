package net.sebinson.framework.message.transport.protocol;

import java.io.Serializable;
import java.util.Date;

import net.sebinson.common.utils.HostUtil;
import net.sebinson.common.utils.TimeUtil;
import net.sebinson.common.utils.UUIDUtil;

/**
 * Message header between client and server communication
 * 
 * @author sebinson@163.com
 *
 */
public class Header implements Serializable {

    private static final long serialVersionUID = 4642888275245098817L;

    /**
     * [Required] Request type [0 request,1 response]
     */
    private int               code             = 0;

    /**
     * [Required] Response type [0 synchronous, 1 asynchronous, 2 no-response]
     */
    private int               rpc              = 1;

    /**
     * [Required] Serial number
     */
    private String            serial           = UUIDUtil.getUUID();

    /**
     * [Required] Interface type
     */
    private String            itype            = "";

    /**
     * [Required] Request identifier, such as the transporter mac address,
     * mobile phone token, server ip.
     */
    private String            add              = HostUtil.getHostAddress();

    /**
     * [Required] Create time [yyyyMMddHHmmssSSS]
     */
    private String            ctime            = TimeUtil.getCurrentDateTime("yyyyMMddHHmmssSSS");

    /**
     * The sign string, default value "SAMPLE"
     */
    private String            sign             = "SAMPLE";

    /**
     * [0 success, other error code]
     */
    private String            result           = "";

    /**
     * description
     */
    private String            msg              = "";

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getRpc() {
        return this.rpc;
    }

    public void setRpc(int rpc) {
        this.rpc = rpc;
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getItype() {
        return this.itype;
    }

    public void setItype(String itype) {
        this.itype = itype;
    }

    public String getAdd() {
        return this.add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getCtime() {
        return this.ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCtimeLong(long ctimeLong) {
        if (ctimeLong <= 0) {
            return;
        }
        this.ctime = TimeUtil.convertDateToString(new Date(ctimeLong), "yyyyMMddHHmmssSSS");
    }

    @Override
    public String toString() {
        return "Header [code=" + this.code + ", rpc=" + this.rpc + ", serial=" + this.serial + ", itype=" + this.itype + ", add=" + this.add + ", ctime="
                + this.ctime + ", sign=" + this.sign + ", result=" + this.result + ", msg=" + this.msg + "]";
    }
}
