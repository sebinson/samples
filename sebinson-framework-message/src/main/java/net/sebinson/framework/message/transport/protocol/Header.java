package net.sebinson.framework.message.transport.protocol;

import java.io.Serializable;
import java.util.Date;

import net.sebinson.common.utils.HostUtil;
import net.sebinson.common.utils.TimeUtil;
import net.sebinson.common.utils.UUIDUtil;

/**
 * Message header between client and server communication
 * 
 * @author C
 */
public class Header implements Serializable {

    private static final long serialVersionUID = 4642888275245098817L;
    /* Required, request type:0 request,1 response */
    private int               code             = 0;
    /* Response type: 0 synchronous, 1 asynchronous, 2 no-response Required */
    private int               rpc              = 1;
    /* [Required] Serial number */
    private String            serial           = UUIDUtil.getUUID();
    /* [Required] Interface type */
    private String            itype            = "";
    /* [Required] Interface version */
    private String            version          = "1.0";
    /* [Required] Request identifier, such as the transporter mac address, mobile phone token, server ip. */
    private String            add              = HostUtil.getHostAddress();
    /* [Required] Create time [yyyyMMddHHmmssSSS] */
    private String            ctime            = TimeUtil.getCurrentDateTime("yyyyMMddHHmmssSSS");
    /* [0 success, other error code] */
    private String            result           = "";
    /* description */
    private String            msg              = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getRpc() {
        return rpc;
    }

    public void setRpc(int rpc) {
        this.rpc = rpc;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getItype() {
        return itype;
    }

    public void setItype(String itype) {
        this.itype = itype;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setCtimeLong(long ctimeLong) {
        if (ctimeLong <= 0) {
            return;
        }
        this.ctime = TimeUtil.convertDateToString(new Date(ctimeLong), "yyyyMMddHHmmssSSS");
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Header [code=" + code + ", rpc=" + rpc + ", serial=" + serial + ", itype=" + itype + ", version=" + version + ", add=" + add + ", ctime="
                + ctime + ", result=" + result + ", msg=" + msg + "]";
    }

}
