package net.sebinson.framework.message.common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientInfoMsg implements Serializable {

    private static final long   serialVersionUID = 8300019109002416029L;

    private String              add              = "";                   // 用户（转发器）标识，如mac地址
    private String              shopid           = "";                   // 商家id
    private String              shopentityid     = "";                   // 实体店Id
    private List<String>        functions;                               // 用户（转发器）的所有功能点
    private long                monitertime;                             // 监控时间
    private String              ctime;                                   // 此ClientInfoMsg创建时间
    private Map<String, Object> param;                                   // 一些用于扩展的数据

    public ClientInfoMsg(String add) {
        if (add != null) {
            this.add = add;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        this.ctime = df.format(new Date());
        this.param = new HashMap<String, Object>();
    }

    public String getAdd() {
        return this.add;
    }

    public void setAdd(String add) {
        this.add = add;
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

    public List<String> getFunctions() {
        return this.functions;
    }

    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }

    public long getMonitertime() {
        return this.monitertime;
    }

    public void setMonitertime(long monitertime) {
        this.monitertime = monitertime;
    }

    public String getCtime() {
        return this.ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public Map<String, Object> getParam() {
        return this.param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ClientInfoMsg [add=" + this.add + ", shopid=" + this.shopid + ", shopentityid=" + this.shopentityid + ", functions=" + this.functions
                + ", monitertime=" + this.monitertime + ", ctime=" + this.ctime + ", param=" + this.param + "]";
    }
}
