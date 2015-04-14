package com.gooagoo.base.mis.org.vo;

import java.io.Serializable;
import java.util.Date;

public class FSysDistributeConfig implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String id;
	private String partnerId;
	private String businessType;
	private String distributeType;
	private String distributeUrl;
	private String encryptKey;
	private String authType;
	private String shopId;
	private String entityId;
	private String brandId;
	private String areaId;
	private String isDel;
	private Date createTime;
	private Date cTimeStamp;
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getPartnerId(){
		return this.partnerId;
	}
	public void setPartnerId(String partnerId){
		this.partnerId = partnerId;
	}
	public String getBusinessType(){
		return this.businessType;
	}
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}
	public String getDistributeType(){
		return this.distributeType;
	}
	public void setDistributeType(String distributeType){
		this.distributeType = distributeType;
	}
	public String getDistributeUrl(){
		return this.distributeUrl;
	}
	public void setDistributeUrl(String distributeUrl){
		this.distributeUrl = distributeUrl;
	}
	public String getEncryptKey(){
		return this.encryptKey;
	}
	public void setEncryptKey(String encryptKey){
		this.encryptKey = encryptKey;
	}
	public String getAuthType(){
		return this.authType;
	}
	public void setAuthType(String authType){
		this.authType = authType;
	}
	public String getShopId(){
		return this.shopId;
	}
	public void setShopId(String shopId){
		this.shopId = shopId;
	}
	public String getEntityId(){
		return this.entityId;
	}
	public void setEntityId(String entityId){
		this.entityId = entityId;
	}
	public String getBrandId(){
		return this.brandId;
	}
	public void setBrandId(String brandId){
		this.brandId = brandId;
	}
	public String getAreaId(){
		return this.areaId;
	}
	public void setAreaId(String areaId){
		this.areaId = areaId;
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