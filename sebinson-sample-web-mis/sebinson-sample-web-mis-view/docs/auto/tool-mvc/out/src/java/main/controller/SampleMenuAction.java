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
import com.gooagoo.sample.service.SampleMenuService;
import com.gooagoo.sample.verify.SampleMenuVerify;
import com.gooagoo.sample.vo.FSampleMenu;

@Controller
@RequestMapping("/sampleMenu")
public class SampleMenuAction extends BaseAction
{
    @Autowired
    private SampleMenuService sampleMenuService;

    /**
     * 主页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, HttpServletResponse response)
    {
        return "samplemenu/index";
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

        FSampleMenu sampleMenu = ServletUtils.objectMethod(FSampleMenu.class, request);
        List<FSampleMenu> list = this.sampleMenuService.listSampleMenu(shopVo, sampleMenu);
        request.setAttribute("list", list);
        return "samplemenu/samplemenu_list";
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

        FSampleMenu sampleMenu = ServletUtils.objectMethod(FSampleMenu.class, request);
        PageCondition condition = ServletUtils.objectMethod(PageCondition.class, request);
        PageModel<FSampleMenu> pm = this.sampleMenuService.pageSampleMenu(shopVo, sampleMenu, condition);
        ShopServletUtil.writePageModelToPage(pm, request);

        return "samplemenu/samplemenu_list";
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
        return "samplemenu/samplemenu_form";
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

        FSampleMenu sampleMenu = ServletUtils.objectMethod(FSampleMenu.class, request);
        ResultVo result = SampleMenuVerify.verifyForm(sampleMenu);
        if (result.isSuccess())
        {
            result = this.sampleMenuService.update(shopVo, sampleMenu);
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

        String menuId = ServletRequestUtils.getStringParameter(request, "menuId","");
        ResultVo result = SampleMenuVerify.verifyID(menuId);
        if (result.isSuccess())
        {
            result = this.sampleMenuService.delete(shopVo, menuId);
        }
        ShopServletUtil.writeJsonToPage(result, response);
    }

    private void form(HttpServletRequest request, HttpServletResponse response, String formType) throws Exception
    {
        ShopVo shopVo = ShopServletUtil.getShopVo(request);

        if (!"A".equals(formType))
        {
            String menuId = ServletRequestUtils.getStringParameter(request, "menuId","");
            FSampleMenu sampleMenu = this.sampleMenuService.getSampleMenu(shopVo, menuId);
            request.setAttribute("sampleMenu", sampleMenu);
        }
        request.setAttribute("formType", formType);
    }

}