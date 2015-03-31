package net.sebinson.sample.web.mis.persistence.domain;

public class SampleMenuOperation {
    private String operId;

    private String menuId;

    private String operCode;

    private String operName;

    private String operType;

    private String operActions;

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode == null ? null : operCode.trim();
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName == null ? null : operName.trim();
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType == null ? null : operType.trim();
    }

    public String getOperActions() {
        return operActions;
    }

    public void setOperActions(String operActions) {
        this.operActions = operActions == null ? null : operActions.trim();
    }
}