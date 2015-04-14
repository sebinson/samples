package com.gooagoo.sample.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.ServletRequestUtils;

import com.gooagoo.common.action.BaseAction;
import com.gooagoo.common.utils.ServletUtils;
import com.gooagoo.shop.common.utils.ShopServletUtil;
import com.gooagoo.shop.common.vo.PageCondition;
import com.gooagoo.shop.common.vo.PageModel;
import com.gooagoo.shop.common.vo.ResultVo;
import com.gooagoo.shop.common.vo.ShopVo;
import com.gooagoo.sample.service.SampleRoleService;
import com.gooagoo.sample.verify.SampleRoleVerify;
import com.gooagoo.sample.vo.FSampleRole;

@Controller
@RequestMapping("/sampleRole")
public class SampleRoleAction extends BaseAction
{
    @Autowired
    private SampleRoleService sampleRoleService;

    /**
     * 主页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, HttpServletResponse response)
    {
        return "samplerole/index";
    }

    /**
     * 列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=list")
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ShopVo shopVo = ShopServletUtil.getShopVo(request);

        FSampleRole sampleRole = ServletUtils.objectMethod(FSampleRole.class, request);
        List<FSampleRole> list = this.sampleRoleService.listSampleRole(shopVo, sampleRole);
        request.setAttribute("list", list);
        return "samplerole/samplerole_list";
    }

    /**
     * 列表(分页)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=page")
    public String page(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ShopVo shopVo = ShopServletUtil.getShopVo(request);

        FSampleRole sampleRole = ServletUtils.objectMethod(FSampleRole.class, request);
        PageCondition condition = ServletUtils.objectMethod(PageCondition.class, request);
        PageModel<FSampleRole> pm = this.sampleRoleService.pageSampleRole(shopVo, sampleRole, condition);
        ShopServletUtil.writePageModelToPage(pm, request);

        return "samplerole/samplerole_list";
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
        return "samplerole/samplerole_form";
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
        ShopVo shopVo = ShopServletUtil.getShopVo(request);

        FSampleRole sampleRole = ServletUtils.objectMethod(FSampleRole.class, request);
        ResultVo result = SampleRoleVerify.verifyForm(sampleRole);
        if (result.isSuccess())
        {
            result = this.sampleRoleService.update(shopVo, sampleRole);
        }
        ShopServletUtil.writeJsonToPage(result, response);
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
        ShopVo shopVo = ShopServletUtil.getShopVo(request);

        String roleId = ServletRequestUtils.getStringParameter(request, "roleId","");
        ResultVo result = SampleRoleVerify.verifyID(roleId);
        if (result.isSuccess())
        {
            result = this.sampleRoleService.delete(shopVo, roleId);
        }
        ShopServletUtil.writeJsonToPage(result, response);
    }

    private void form(HttpServletRequest request, HttpServletResponse response, String formType) throws Exception
    {
        ShopVo shopVo = ShopServletUtil.getShopVo(request);

        if (!"A".equals(formType))
        {
            String roleId = ServletRequestUtils.getStringParameter(request, "roleId","");
            FSampleRole sampleRole = this.sampleRoleService.getSampleRole(shopVo, roleId);
            request.setAttribute("sampleRole", sampleRole);
        }
        request.setAttribute("formType", formType);
    }

}