package com.gooagoo.base.mis.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gooagoo.base.mis.device.service.AnalyzeTemplateService;
import com.gooagoo.base.mis.device.vo.FAnalyzeTemplate;
import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.common.vo.LoginVo;
import com.google.gson.Gson;

@Service(value = "AnalyzeTemplateService")
public class AnalyzeTemplateServiceImpl implements AnalyzeTemplateService
{
    private final Gson gson = new Gson();

    @Override
    public ResultVo add(LoginVo loginVo, FAnalyzeTemplate analyzeTemplate) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("AnalyzeTemplate:" + this.gson.toJson(analyzeTemplate));
        return new ResultVo(false, "还在维护中...");
    }

    @Override
    public ResultVo update(LoginVo loginVo, FAnalyzeTemplate analyzeTemplate) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("AnalyzeTemplate:" + this.gson.toJson(analyzeTemplate));
        return new ResultVo(false, "还在维护中...");
    }

    @Override
    public ResultVo delete(LoginVo loginVo, String analyzeTemplateId)
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("analyzeTemplateId:" + analyzeTemplateId);
        return new ResultVo(false, "还在维护中...");
    }

    @Override
    public FAnalyzeTemplate getAnalyzeTemplate(LoginVo loginVo, String analyzeTemplateId) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("analyzeTemplateId:" + analyzeTemplateId);
        FAnalyzeTemplate analyzeTemplate = new FAnalyzeTemplate();
        analyzeTemplate.setAnalyzeTemplateId(analyzeTemplateId);
        analyzeTemplate.setType("type");
        analyzeTemplate.setName("name");
        analyzeTemplate.setUrl("url");
        analyzeTemplate.setStatus("status");
        analyzeTemplate.setVer("ver");
        analyzeTemplate.setTemplateMd5("templateMd5");
        analyzeTemplate.setRemark("remark");
        analyzeTemplate.setIsDel("isDel");
        return analyzeTemplate;
    }

    @Override
    public List<FAnalyzeTemplate> listAnalyzeTemplate(LoginVo loginVo, FAnalyzeTemplate analyzeTemplate) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("AnalyzeTemplate:" + this.gson.toJson(analyzeTemplate));

        List<FAnalyzeTemplate> list = new ArrayList<FAnalyzeTemplate>();
        analyzeTemplate.setAnalyzeTemplateId("1");
        analyzeTemplate.setType("type");
        analyzeTemplate.setName("name");
        analyzeTemplate.setUrl("url");
        analyzeTemplate.setStatus("status");
        analyzeTemplate.setVer("ver");
        analyzeTemplate.setTemplateMd5("templateMd5");
        analyzeTemplate.setRemark("remark");
        analyzeTemplate.setIsDel("isDel");

        list.add(analyzeTemplate);
        return null;
    }

    @Override
    public PageModel<FAnalyzeTemplate> pageAnalyzeTemplate(LoginVo loginVo, FAnalyzeTemplate analyzeTemplate, PageCondition condition) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("AnalyzeTemplate:" + this.gson.toJson(analyzeTemplate));
        System.out.println("condition:" + this.gson.toJson(condition));

        PageModel<FAnalyzeTemplate> pm = new PageModel<FAnalyzeTemplate>();
        pm.setPageIndex(condition.getPageIndex());
        pm.setPageSize(condition.getPageSize());

        analyzeTemplate.setAnalyzeTemplateId("1");
        analyzeTemplate.setType("type");
        analyzeTemplate.setName("name");
        analyzeTemplate.setUrl("url");
        analyzeTemplate.setStatus("status");
        analyzeTemplate.setVer("ver");
        analyzeTemplate.setTemplateMd5("templateMd5");
        analyzeTemplate.setRemark("remark");
        analyzeTemplate.setIsDel("isDel");
        pm.getRows().add(analyzeTemplate);
        pm.setTotal(condition.getPageSize() + 1);
        return pm;
    }
 
}