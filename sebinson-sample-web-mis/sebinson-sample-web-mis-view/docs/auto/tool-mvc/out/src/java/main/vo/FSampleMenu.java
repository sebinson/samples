package com.gooagoo.sample.vo;

import java.io.Serializable;

public class FSampleMenu implements Serializable
{
	private static final long serialVersionUID = 1L;

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
	public String getMenuId(){
		return this.menuId;
	}
	public void setMenuId(String menuId){
		this.menuId = menuId;
	}
	public String getMenuCode(){
		return this.menuCode;
	}
	public void setMenuCode(String menuCode){
		this.menuCode = menuCode;
	}
	public String getMenuType(){
		return this.menuType;
	}
	public void setMenuType(String menuType){
		this.menuType = menuType;
	}
	public String getMenuName(){
		return this.menuName;
	}
	public void setMenuName(String menuName){
		this.menuName = menuName;
	}
	public String getMenuStatus(){
		return this.menuStatus;
	}
	public void setMenuStatus(String menuStatus){
		this.menuStatus = menuStatus;
	}
	public String getMenuParentId(){
		return this.menuParentId;
	}
	public void setMenuParentId(String menuParentId){
		this.menuParentId = menuParentId;
	}
	public Integer getMenuOrder(){
		return this.menuOrder;
	}
	public void setMenuOrder(Integer menuOrder){
		this.menuOrder = menuOrder;
	}
	public String getMenuUri(){
		return this.menuUri;
	}
	public void setMenuUri(String menuUri){
		this.menuUri = menuUri;
	}
	public String getMenuActions(){
		return this.menuActions;
	}
	public void setMenuActions(String menuActions){
		this.menuActions = menuActions;
	}
	public String getMenuDesc(){
		return this.menuDesc;
	}
	public void setMenuDesc(String menuDesc){
		this.menuDesc = menuDesc;
	}
}