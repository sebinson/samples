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
import com.gooagoo.sample.service.SampleMenuOperationService;
import com.gooagoo.sample.verify.SampleMenuOperationVerify;
import com.gooagoo.sample.vo.FSampleMenuOperation;

@Controller
@RequestMapping("/sampleMenuOperation")
public class SampleMenuOperationAction extends BaseAction
{
    @Autowired
    private SampleMenuOperationService sampleMenuOperationService;

    /**
     * 主页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, HttpServletResponse response)
    {
        return "samplemenuoperation/index";
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

        FSampleMenuOperation sampleMenuOperation = ServletUtils.objectMethod(FSampleMenuOperation.class, request);
        List<FSampleMenuOperation> list = this.sampleMenuOperationService.listSampleMenuOperation(shopVo, sampleMenuOperation);
        request.setAttribute("list", list);
        return "samplemenuoperation/samplemenuoperation_list";
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

        FSampleMenuOperation sampleMenuOperation = ServletUtils.objectMethod(FSampleMenuOperation.class, request);
        PageCondition condition = ServletUtils.objectMethod(PageCondition.class, request);
        PageModel<FSampleMenuOperation> pm = this.sampleMenuOperationService.pageSampleMenuOperation(shopVo, sampleMenuOperation, condition);
        ShopServletUtil.writePageModelToPage(pm, request);

        return "samplemenuoperation/samplemenuoperation_list";
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
        return "samplemenuoperation/samplemenuoperation_form";
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

        FSampleMenuOperation sampleMenuOperation = ServletUtils.objectMethod(FSampleMenuOperation.class, request);
        ResultVo result = SampleMenuOperationVerify.verifyForm(sampleMenuOperation);
        if (result.isSuccess())
        {
            result = this.sampleMenuOperationService.update(shopVo, sampleMenuOperation);
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

        String operId = ServletRequestUtils.getStringParameter(request, "operId","");
        ResultVo result = SampleMenuOperationVerify.verifyID(operId);
        if (result.isSuccess())
        {
            result = this.sampleMenuOperationService.delete(shopVo, operId);
        }
        ShopServletUtil.writeJsonToPage(result, response);
    }

    private void form(HttpServletRequest request, HttpServletResponse response, String formType) throws Exception
    {
        ShopVo shopVo = ShopServletUtil.getShopVo(request);

        if (!"A".equals(formType))
        {
            String operId = ServletRequestUtils.getStringParameter(request, "operId","");
            FSampleMenuOperation sampleMenuOperation = this.sampleMenuOperationService.getSampleMenuOperation(shopVo, operId);
            request.setAttribute("sampleMenuOperation", sampleMenuOperation);
        }
        request.setAttribute("formType", formType);
    }

}