package net.sebinson.common.log;

import java.io.Serializable;

public class LogParam implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8473514270414324936L;
    private String            puuid;                                   //父级流水号
    private String            fuuid;                                   //流程流水号
    private String            userType;                                //用户类型
    private String            userId;                                  //用户编号
    private String            source;                                  //来源
    private String            clientIp;                                //客户端IP
    private String            groupId;                                 //组织编号

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

    @Override
    public String toString() {
        return "LogParam [puuid=" + puuid + ", fuuid=" + fuuid + ", userType=" + userType + ", userId=" + userId + ", source=" + source + ", clientIp="
                + clientIp + ", groupId=" + groupId + "]";
    }

}
