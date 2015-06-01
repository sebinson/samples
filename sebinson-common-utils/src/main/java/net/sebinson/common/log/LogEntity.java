package net.sebinson.common.log;

import java.io.Serializable;

public class LogEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2216456460228595383L;

    private String            uuid;                                    //流水号
    private String            puuid;                                   //父级流水号
    private String            fuuid;                                   //流程流水号
    private String            module;                                  //模块编码
    private String            itemName;                                //项目名称
    private String            itemType;                                //项目类型（war/jar）
    private String            serverIp;                                //服务器IP
    private String            iCode;                                   //接口编码
    private String            userType;                                //用户类型
    private String            userId;                                  //用户编号
    private String            source;                                  //来源
    private String            clientIp;                                //客户端IP
    private String            groupId;                                 //组织编号
    private String            weight;                                  //权重
    private String            behaveType;                              //行为类型
    private String            objectType;                              //对象类型
    private String            objectCode;                              //对象编号
    private String            keyword;                                 //关键字
    private String            result;                                  //结果
    private String            content;                                 //内容
    private String            alarmType;                               //报警类型
    private String            alarmContent;                            //报警内容
    private String            logType;                                 //日志类型
    private String            logName;                                 //日志名称
    private String            error;                                   //错误编码
    private String            message;                                 //错误信息
    private String            level;                                   //日志等级
    private String            createTime;                              //创建时间

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getFuuid() {
        return fuuid;
    }

    public void setFuuid(String fuuid) {
        this.fuuid = fuuid;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getiCode() {
        return iCode;
    }

    public void setiCode(String iCode) {
        this.iCode = iCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBehaveType() {
        return behaveType;
    }

    public void setBehaveType(String behaveType) {
        this.behaveType = behaveType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Log [uuid=" + uuid + ", puuid=" + puuid + ", fuuid=" + fuuid + ", module=" + module + ", itemName=" + itemName + ", itemType=" + itemType
                + ", serverIp=" + serverIp + ", iCode=" + iCode + ", userType=" + userType + ", userId=" + userId + ", source=" + source + ", clientIp="
                + clientIp + ", groupId=" + groupId + ", weight=" + weight + ", behaveType=" + behaveType + ", objectType=" + objectType + ", objectCode="
                + objectCode + ", keyword=" + keyword + ", result=" + result + ", content=" + content + ", alarmType=" + alarmType + ", alarmContent="
                + alarmContent + ", logType=" + logType + ", logName=" + logName + ", error=" + error + ", message=" + message + ", level=" + level
                + ", createTime=" + createTime + "]";
    }
}
