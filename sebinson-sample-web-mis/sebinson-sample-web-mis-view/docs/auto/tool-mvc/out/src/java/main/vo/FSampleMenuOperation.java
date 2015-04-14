package com.gooagoo.sample.vo;

import java.io.Serializable;

public class FSampleMenuOperation implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String operId;
	private String menuId;
	private String operCode;
	private String operName;
	private String operType;
	private String operActions;
	public String getOperId(){
		return this.operId;
	}
	public void setOperId(String operId){
		this.operId = operId;
	}
	public String getMenuId(){
		return this.menuId;
	}
	public void setMenuId(String menuId){
		this.menuId = menuId;
	}
	public String getOperCode(){
		return this.operCode;
	}
	public void setOperCode(String operCode){
		this.operCode = operCode;
	}
	public String getOperName(){
		return this.operName;
	}
	public void setOperName(String operName){
		this.operName = operName;
	}
	public String getOperType(){
		return this.operType;
	}
	public void setOperType(String operType){
		this.operType = operType;
	}
	public String getOperActions(){
		return this.operActions;
	}
	public void setOperActions(String operActions){
		this.operActions = operActions;
	}
}