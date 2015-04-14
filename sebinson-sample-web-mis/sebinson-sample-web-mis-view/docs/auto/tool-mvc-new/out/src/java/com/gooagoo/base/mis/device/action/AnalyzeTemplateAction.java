package com.gooagoo.base.mis.device.action;

import java.util.HashMap;
import java.util.Map;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.ServletRequestUtils;

import com.gooagoo.base.mis.common.action.BaseAction;
import com.gooagoo.common.utils.ServletUtils;
import com.gooagoo.base.mis.common.utils.MisServletUtil;
import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.common.vo.LoginVo;
import com.gooagoo.base.mis.device.service.AnalyzeTemplateService;
import com.gooagoo.base.mis.device.verify.AnalyzeTemplateVerify;
import com.gooagoo.base.mis.device.vo.FAnalyzeTemplate;
import com.gooagoo.common.utils.JsonUtils;
import com.gooagoo.common.utils.ServletUtils;

@Controller
@RequestMapping("/analyzeTemplate")
public class AnalyzeTemplateAction extends BaseAction
{
    @Autowired
    private AnalyzeTemplateService analyzeTemplateService;

    /**
     * 主页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, HttpServletResponse response)
    {
        return "device/analyzetemplate/analyzetemplate_index";
    }

    /**
     * 列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=list")
    public void list(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);

        FAnalyzeTemplate analyzeTemplate = ServletUtils.objectMethod(FAnalyzeTemplate.class, request);
        List<FAnalyzeTemplate> list = this.analyzeTemplateService.listAnalyzeTemplate(loginVo, analyzeTemplate);
        MisServletUtil.writeJsonListDataToPage(list, response);
    }

    /**
     * 列表(分页)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=page")
    public void page(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);

        FAnalyzeTemplate analyzeTemplate = ServletUtils.objectMethod(FAnalyzeTemplate.class, request);
        PageCondition condition = ServletUtils.objectMethod(PageCondition.class, request);
        PageModel<FAnalyzeTemplate> pm = this.analyzeTemplateService.pageAnalyzeTemplate(loginVo, analyzeTemplate, condition);
        MisServletUtil.writeJsonPageDataToPage(pm, response);
    }

    /**
     * 添加表单页面
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=formA")
    public void formA(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        this.form(request, response, "A");
    }

    /**
     * 添加
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=add")
    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);

        FAnalyzeTemplate analyzeTemplate = ServletUtils.objectMethod(FAnalyzeTemplate.class, request);
        ResultVo result = AnalyzeTemplateVerify.verifyForm(analyzeTemplate);
        if (result.isSuccess())
        {
            result = this.analyzeTemplateService.add(loginVo, analyzeTemplate);
        }
        MisServletUtil.writeJsonToPage(result, response);
    }

    /**
     * 修改表单页面
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=formU")
    public void formU(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        this.form(request, response, "U");
    }

    /**
     * 修改
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=update")
    public void update(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);

        FAnalyzeTemplate analyzeTemplate = ServletUtils.objectMethod(FAnalyzeTemplate.class, request);
        ResultVo result = AnalyzeTemplateVerify.verifyForm(analyzeTemplate);
        if (result.isSuccess())
        {
            result = this.analyzeTemplateService.update(loginVo, analyzeTemplate);
        }
        MisServletUtil.writeJsonToPage(result, response);
    }

    /**
     * 删除
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);

        String analyzeTemplateId = ServletRequestUtils.getStringParameter(request, "analyzeTemplateId","");
        ResultVo result = AnalyzeTemplateVerify.verifyID(analyzeTemplateId);
        if (result.isSuccess())
        {
            result = this.analyzeTemplateService.delete(loginVo, analyzeTemplateId);
        }
        MisServletUtil.writeJsonToPage(result, response);
    }

    private void form(HttpServletRequest request, HttpServletResponse response, String formType) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);
        Map<String, Object> result = new HashMap<String, Object>();
        boolean success = false;
        Object data = null;

        if (!"A".equals(formType))
        {
            String analyzeTemplateId = ServletRequestUtils.getStringParameter(request, "analyzeTemplateId","");
            data = this.analyzeTemplateService.getAnalyzeTemplate(loginVo, analyzeTemplateId);
        }
        success = true;
        result.put("success", success);
        result.put("data", data);
        result.put("formType", formType);
        ServletUtils.writeString(JsonUtils.toJson(result), response);
    }

}