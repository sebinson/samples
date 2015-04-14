package com.gooagoo.sample.vo;

import java.io.Serializable;

public class FSampleUser implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String userId;
	private String userName;
	private String password;
	private Date cdt;
	private Date udt;
	public String getUserId(){
		return this.userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public Date getCdt(){
		return this.cdt;
	}
	public void setCdt(Date cdt){
		this.cdt = cdt;
	}
	public Date getUdt(){
		return this.udt;
	}
	public void setUdt(Date udt){
		this.udt = udt;
	}
}