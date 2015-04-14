package com.gooagoo.base.mis.device.vo;

import java.io.Serializable;
import java.util.Date;

public class FAnalyzeTemplate implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String analyzeTemplateId;
	private String type;
	private String name;
	private String url;
	private String status;
	private String ver;
	private String templateMd5;
	private String remark;
	private String isDel;
	private Date createTime;
	private Date cTimeStamp;
	public String getAnalyzeTemplateId(){
		return this.analyzeTemplateId;
	}
	public void setAnalyzeTemplateId(String analyzeTemplateId){
		this.analyzeTemplateId = analyzeTemplateId;
	}
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getUrl(){
		return this.url;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getVer(){
		return this.ver;
	}
	public void setVer(String ver){
		this.ver = ver;
	}
	public String getTemplateMd5(){
		return this.templateMd5;
	}
	public void setTemplateMd5(String templateMd5){
		this.templateMd5 = templateMd5;
	}
	public String getRemark(){
		return this.remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getIsDel(){
		return this.isDel;
	}
	public void setIsDel(String isDel){
		this.isDel = isDel;
	}
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Date getCTimeStamp(){
		return this.cTimeStamp;
	}
	public void setCTimeStamp(Date cTimeStamp){
		this.cTimeStamp = cTimeStamp;
	}
}