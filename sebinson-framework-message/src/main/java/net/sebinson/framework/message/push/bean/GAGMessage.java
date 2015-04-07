package net.sebinson.framework.message.push.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 发送的消息
 *
 * @param <T>
 *            业务实体
 */
public class GAGMessage<T> implements Serializable {
    private static final long serialVersionUID = 8300019109002416019L;

    private String            serial;                                        // uuid
                                                                             // 流水号
    private String[]          receiveid;                                     // 接收者
    private String            shopid;                                        // 商家id
    private String            shopentityid;                                  // 实体店id
    private String            itype;                                         // 消息类型，如开发票消息，退房消息
    private int               rpc              = 1;                          // 0整数:处理完数据后再应答（同步应答）为0，接收完数据没有处理数据直接应答（异步应答）为1，接收数据后不应答为2
    private T                 data;                                          // 业务数据
    private byte[]            binary;                                        // 业务数据，如文件private
                                                                             // String
    private String            source;                                        // 消息来源
    private int               flag;                                          // 操作结果,0-消息没有发送，1-消息发送成功，2-消息发送失败....
    private int               rsend;                                         // 0发送失败后保留重发;1发送失败后不重发而舍弃
    private int               allpush;                                       // 0从接收者里随机取一个发送;1给所有接收者群发
    private long              timeout          = 259200000;                  // 消息过期时间,毫秒
                                                                             // 默认为3天
    private long              ctime            = System.currentTimeMillis(); // 消息创建时间，毫秒
                                                                             // 默认

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String[] getReceiveid() {
        return this.receiveid;
    }

    public void setReceiveid(String[] receiveid) {
        this.receiveid = receiveid;
    }

    public String getShopid() {
        return this.shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getShopentityid() {
        return this.shopentityid;
    }

    public void setShopentityid(String shopentityid) {
        this.shopentityid = shopentityid;
    }

    public String getItype() {
        return this.itype;
    }

    public void setItype(String itype) {
        this.itype = itype;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public byte[] getBinary() {
        return this.binary;
    }

    public void setBinary(byte[] binary) {
        this.binary = binary;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getRsend() {
        return this.rsend;
    }

    public void setRsend(int rsend) {
        this.rsend = rsend;
    }

    public int getAllpush() {
        return this.allpush;
    }

    public void setAllpush(int allpush) {
        this.allpush = allpush;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getCtime() {
        return this.ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public int getRpc() {
        return this.rpc;
    }

    public void setRpc(int rpc) {
        this.rpc = rpc;
    }

    @Override
    public String toString() {
        return "GAGMessage [serial=" + this.serial + ", receiveid=" + Arrays.toString(this.receiveid) + ", shopid=" + this.shopid + ", shopentityid="
                + this.shopentityid + ", itype=" + this.itype + ", rpc=" + this.rpc + ", data=" + this.data + ", binary.size="
                + (this.binary == null ? "0" : this.binary.length) + ", source=" + this.source + ", flag=" + this.flag + ", rsend=" + this.rsend + ", allpush="
                + this.allpush + ", timeout=" + this.timeout + ", ctime=" + this.ctime + "]";
    }

}
