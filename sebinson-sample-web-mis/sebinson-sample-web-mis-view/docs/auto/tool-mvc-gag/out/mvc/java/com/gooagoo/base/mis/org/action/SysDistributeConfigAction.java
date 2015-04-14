package com.gooagoo.base.mis.org.action;

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
import com.gooagoo.base.mis.org.service.SysDistributeConfigService;
import com.gooagoo.base.mis.org.verify.SysDistributeConfigVerify;
import com.gooagoo.base.mis.org.vo.FSysDistributeConfig;
import com.gooagoo.common.utils.JsonUtils;

@Controller
@RequestMapping("/sysDistributeConfig")
public class SysDistributeConfigAction extends BaseAction
{
    @Autowired
    private SysDistributeConfigService sysDistributeConfigService;

    /**
     * 主页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, HttpServletResponse response)
    {
        return "org/sysdistributeconfig/sysdistributeconfig_index";
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

        FSysDistributeConfig sysDistributeConfig = ServletUtils.objectMethod(FSysDistributeConfig.class, request);
        List<FSysDistributeConfig> list = this.sysDistributeConfigService.listSysDistributeConfig(loginVo, sysDistributeConfig);
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

        FSysDistributeConfig sysDistributeConfig = ServletUtils.objectMethod(FSysDistributeConfig.class, request);
        PageCondition condition = ServletUtils.objectMethod(PageCondition.class, request);
        PageModel<FSysDistributeConfig> pm = this.sysDistributeConfigService.pageSysDistributeConfig(loginVo, sysDistributeConfig, condition);
        MisServletUtil.writeJsonPageDataToPage(pm, response);
    }

    /**
     * 添加表单页面
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=formA")
    public String formA(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        this.form(request, response, "A");
        return "org/sysdistributeconfig/sysdistributeconfig_form";
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

        FSysDistributeConfig sysDistributeConfig = ServletUtils.objectMethod(FSysDistributeConfig.class, request);
        ResultVo result = SysDistributeConfigVerify.verifyForm(sysDistributeConfig);
        if (result.isSuccess())
        {
            result = this.sysDistributeConfigService.add(loginVo, sysDistributeConfig);
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
    public String formU(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        this.form(request, response, "U");
        return "org/sysdistributeconfig/sysdistributeconfig_form";
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

        FSysDistributeConfig sysDistributeConfig = ServletUtils.objectMethod(FSysDistributeConfig.class, request);
        ResultVo result = SysDistributeConfigVerify.verifyForm(sysDistributeConfig);
        if (result.isSuccess())
        {
            result = this.sysDistributeConfigService.update(loginVo, sysDistributeConfig);
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

        String id = ServletRequestUtils.getStringParameter(request, "id","");
        ResultVo result = SysDistributeConfigVerify.verifyID(id);
        if (result.isSuccess())
        {
            result = this.sysDistributeConfigService.delete(loginVo, id);
        }
        MisServletUtil.writeJsonToPage(result, response);
    }

    private void form(HttpServletRequest request, HttpServletResponse response, String formType) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);
        boolean success = false;
        Object data = null;

        if (!"A".equals(formType))
        {
            String id = ServletRequestUtils.getStringParameter(request, "id","");
            data = this.sysDistributeConfigService.getSysDistributeConfig(loginVo, id);
        }
        success = true;
        request.setAttribute("success", success);
        request.setAttribute("data", data);
        request.setAttribute("formType", formType);
    }

}