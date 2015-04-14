package com.gooagoo.sample.vo;

import java.io.Serializable;

public class FSampleRole implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String roleId;
	private String roleCode;
	private String roleName;
	private String roleDesc;
	public String getRoleId(){
		return this.roleId;
	}
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}
	public String getRoleCode(){
		return this.roleCode;
	}
	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
	}
	public String getRoleName(){
		return this.roleName;
	}
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
	public String getRoleDesc(){
		return this.roleDesc;
	}
	public void setRoleDesc(String roleDesc){
		this.roleDesc = roleDesc;
	}
}