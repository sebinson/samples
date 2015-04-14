package com.gooagoo.base.mis.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gooagoo.base.mis.org.service.SysDistributeConfigService;
import com.gooagoo.base.mis.org.vo.FSysDistributeConfig;
import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.common.vo.LoginVo;
import com.google.gson.Gson;

@Service(value = "SysDistributeConfigService")
public class SysDistributeConfigServiceImpl implements SysDistributeConfigService
{
    private final Gson gson = new Gson();

    @Override
    public ResultVo add(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("SysDistributeConfig:" + this.gson.toJson(sysDistributeConfig));
        return new ResultVo(false, "还在维护中...");
    }

    @Override
    public ResultVo update(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("SysDistributeConfig:" + this.gson.toJson(sysDistributeConfig));
        return new ResultVo(false, "还在维护中...");
    }

    @Override
    public ResultVo delete(LoginVo loginVo, String id)
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("id:" + id);
        return new ResultVo(false, "还在维护中...");
    }

    @Override
    public FSysDistributeConfig getSysDistributeConfig(LoginVo loginVo, String id) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("id:" + id);
        FSysDistributeConfig sysDistributeConfig = new FSysDistributeConfig();
        sysDistributeConfig.setId(id);
        sysDistributeConfig.setPartnerId("partnerId");
        sysDistributeConfig.setBusinessType("businessType");
        sysDistributeConfig.setDistributeType("distributeType");
        sysDistributeConfig.setDistributeUrl("distributeUrl");
        sysDistributeConfig.setEncryptKey("encryptKey");
        sysDistributeConfig.setAuthType("authType");
        sysDistributeConfig.setShopId("shopId");
        sysDistributeConfig.setEntityId("entityId");
        sysDistributeConfig.setBrandId("brandId");
        sysDistributeConfig.setAreaId("areaId");
        sysDistributeConfig.setIsDel("isDel");
        return sysDistributeConfig;
    }

    @Override
    public List<FSysDistributeConfig> listSysDistributeConfig(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("SysDistributeConfig:" + this.gson.toJson(sysDistributeConfig));

        List<FSysDistributeConfig> list = new ArrayList<FSysDistributeConfig>();
        sysDistributeConfig.setId("1");
        sysDistributeConfig.setPartnerId("partnerId");
        sysDistributeConfig.setBusinessType("businessType");
        sysDistributeConfig.setDistributeType("distributeType");
        sysDistributeConfig.setDistributeUrl("distributeUrl");
        sysDistributeConfig.setEncryptKey("encryptKey");
        sysDistributeConfig.setAuthType("authType");
        sysDistributeConfig.setShopId("shopId");
        sysDistributeConfig.setEntityId("entityId");
        sysDistributeConfig.setBrandId("brandId");
        sysDistributeConfig.setAreaId("areaId");
        sysDistributeConfig.setIsDel("isDel");

        list.add(sysDistributeConfig);
        return null;
    }

    @Override
    public PageModel<FSysDistributeConfig> pageSysDistributeConfig(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig, PageCondition condition) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("SysDistributeConfig:" + this.gson.toJson(sysDistributeConfig));
        System.out.println("condition:" + this.gson.toJson(condition));

        PageModel<FSysDistributeConfig> pm = new PageModel<FSysDistributeConfig>();
        pm.setPageIndex(condition.getPageIndex());
        pm.setPageSize(condition.getPageSize());

        sysDistributeConfig.setId("1");
        sysDistributeConfig.setPartnerId("partnerId");
        sysDistributeConfig.setBusinessType("businessType");
        sysDistributeConfig.setDistributeType("distributeType");
        sysDistributeConfig.setDistributeUrl("distributeUrl");
        sysDistributeConfig.setEncryptKey("encryptKey");
        sysDistributeConfig.setAuthType("authType");
        sysDistributeConfig.setShopId("shopId");
        sysDistributeConfig.setEntityId("entityId");
        sysDistributeConfig.setBrandId("brandId");
        sysDistributeConfig.setAreaId("areaId");
        sysDistributeConfig.setIsDel("isDel");
        pm.getRows().add(sysDistributeConfig);
        pm.setTotal(condition.getPageSize() + 1);
        return pm;
    }
 
}