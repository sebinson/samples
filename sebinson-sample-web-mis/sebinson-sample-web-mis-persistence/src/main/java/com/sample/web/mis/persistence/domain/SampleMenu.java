package com.sample.web.mis.persistence.domain;

public class SampleMenu {
    private String menuId;

    private String menuCode;

    private String menuType;

    private String menuName;

    private String menuStatus;

    private String menuParentId;

    private Integer menuOrder;

    private String menuUri;

    private String menuActions;

    private String menuDesc;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType == null ? null : menuType.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(String menuStatus) {
        this.menuStatus = menuStatus == null ? null : menuStatus.trim();
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId == null ? null : menuParentId.trim();
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuUri() {
        return menuUri;
    }

    public void setMenuUri(String menuUri) {
        this.menuUri = menuUri == null ? null : menuUri.trim();
    }

    public String getMenuActions() {
        return menuActions;
    }

    public void setMenuActions(String menuActions) {
        this.menuActions = menuActions == null ? null : menuActions.trim();
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc == null ? null : menuDesc.trim();
    }
}