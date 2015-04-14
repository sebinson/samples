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
import com.gooagoo.sample.service.SampleUserService;
import com.gooagoo.sample.verify.SampleUserVerify;
import com.gooagoo.sample.vo.FSampleUser;

@Controller
@RequestMapping("/sampleUser")
public class SampleUserAction extends BaseAction
{
    @Autowired
    private SampleUserService sampleUserService;

    /**
     * 主页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, HttpServletResponse response)
    {
        return "sampleuser/index";
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

        FSampleUser sampleUser = ServletUtils.objectMethod(FSampleUser.class, request);
        List<FSampleUser> list = this.sampleUserService.listSampleUser(shopVo, sampleUser);
        request.setAttribute("list", list);
        return "sampleuser/sampleuser_list";
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

        FSampleUser sampleUser = ServletUtils.objectMethod(FSampleUser.class, request);
        PageCondition condition = ServletUtils.objectMethod(PageCondition.class, request);
        PageModel<FSampleUser> pm = this.sampleUserService.pageSampleUser(shopVo, sampleUser, condition);
        ShopServletUtil.writePageModelToPage(pm, request);

        return "sampleuser/sampleuser_list";
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
        return "sampleuser/sampleuser_form";
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

        FSampleUser sampleUser = ServletUtils.objectMethod(FSampleUser.class, request);
        ResultVo result = SampleUserVerify.verifyForm(sampleUser);
        if (result.isSuccess())
        {
            result = this.sampleUserService.update(shopVo, sampleUser);
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

        String userId = ServletRequestUtils.getStringParameter(request, "userId","");
        ResultVo result = SampleUserVerify.verifyID(userId);
        if (result.isSuccess())
        {
            result = this.sampleUserService.delete(shopVo, userId);
        }
        ShopServletUtil.writeJsonToPage(result, response);
    }

    private void form(HttpServletRequest request, HttpServletResponse response, String formType) throws Exception
    {
        ShopVo shopVo = ShopServletUtil.getShopVo(request);

        if (!"A".equals(formType))
        {
            String userId = ServletRequestUtils.getStringParameter(request, "userId","");
            FSampleUser sampleUser = this.sampleUserService.getSampleUser(shopVo, userId);
            request.setAttribute("sampleUser", sampleUser);
        }
        request.setAttribute("formType", formType);
    }

}