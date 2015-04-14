package ${sheet_uncap}.action;

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
<#if (X_FILEPARA1?index_of("P")>-1)>
import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
</#if>
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.common.vo.LoginVo;
import ${sheet_uncap}.service.${fileid_cap_first}Service;
import ${sheet_uncap}.verify.${fileid_cap_first}Verify;
import ${sheet_uncap}.vo.F${fileid_cap_first};
import com.gooagoo.common.utils.JsonUtils;
import com.gooagoo.common.utils.ServletUtils;

@Controller
@RequestMapping("/${fileid_uncap_first}")
public class ${fileid_cap_first}Action extends BaseAction
{
    @Autowired
    private ${fileid_cap_first}Service ${fileid_uncap_first}Service;

<#if (X_FILEPARA1?index_of("L")>-1)||(X_FILEPARA1?index_of("P")>-1)>
    /**
     * 主页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, HttpServletResponse response)
    {
        return "${X_FILEPARA3}/${fileid_uncap}/${fileid_uncap}_index";
    }

</#if>
<#if (X_FILEPARA1?index_of("L")>-1)>
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

        F${fileid_cap_first} ${fileid_uncap_first} = ServletUtils.objectMethod(F${fileid_cap_first}.class, request);
        List<F${fileid_cap_first}> list = this.${fileid_uncap_first}Service.list${fileid_cap_first}(loginVo, ${fileid_uncap_first});
        MisServletUtil.writeJsonListDataToPage(list, response);
    }

</#if>
<#if (X_FILEPARA1?index_of("P")>-1)>
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

        F${fileid_cap_first} ${fileid_uncap_first} = ServletUtils.objectMethod(F${fileid_cap_first}.class, request);
        PageCondition condition = ServletUtils.objectMethod(PageCondition.class, request);
        PageModel<F${fileid_cap_first}> pm = this.${fileid_uncap_first}Service.page${fileid_cap_first}(loginVo, ${fileid_uncap_first}, condition);
        MisServletUtil.writeJsonPageDataToPage(pm, response);
    }

</#if>
<#if (X_FILEPARA1?index_of("A")>-1)>
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

        F${fileid_cap_first} ${fileid_uncap_first} = ServletUtils.objectMethod(F${fileid_cap_first}.class, request);
        ResultVo result = ${fileid_cap_first}Verify.verifyForm(${fileid_uncap_first});
        if (result.isSuccess())
        {
            result = this.${fileid_uncap_first}Service.add(loginVo, ${fileid_uncap_first});
        }
        MisServletUtil.writeJsonToPage(result, response);
    }

</#if>
<#if (X_FILEPARA1?index_of("U")>-1)>
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

        F${fileid_cap_first} ${fileid_uncap_first} = ServletUtils.objectMethod(F${fileid_cap_first}.class, request);
        ResultVo result = ${fileid_cap_first}Verify.verifyForm(${fileid_uncap_first});
        if (result.isSuccess())
        {
            result = this.${fileid_uncap_first}Service.update(loginVo, ${fileid_uncap_first});
        }
        MisServletUtil.writeJsonToPage(result, response);
    }

</#if>
<#if (X_FILEPARA1?index_of("D")>-1)>
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

<#if (X_FILEPARA2?trim)?split(' ')[0]=='String'>
        String ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getStringParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}","");
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='int'||(X_FILEPARA2?trim)?split(' ')[0]=='Integer' >
        int ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='long'||(X_FILEPARA2?trim)?split(' ')[0]=='Long' >
        long ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getLongParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='float'||(X_FILEPARA2?trim)?split(' ')[0]=='Float' >
        float ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='double'||(X_FILEPARA2?trim)?split(' ')[0]=='Double' >
        double ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#else>
        ${X_FILEPARA2?trim} = ServletUtils.objectMethod(${(X_FILEPARA2?trim)?split(' ')[0]}.class, request);
</#if>
        ResultVo result = ${fileid_cap_first}Verify.verifyID(${X_FILEPARA2?split(" ")[1]});
        if (result.isSuccess())
        {
            result = this.${fileid_uncap_first}Service.delete(loginVo, ${X_FILEPARA2?split(" ")[1]});
        }
        MisServletUtil.writeJsonToPage(result, response);
    }

</#if>
<#if (X_FILEPARA1?index_of("C")>-1)>
    /**
     * 审核页面
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=formC")
    public String formC(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        this.form(request, response, "C");
        return "${X_FILEPARA3}/${fileid_uncap}/${fileid_uncap}_check";
    }

    /**
     * 审核
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=check")
    public void check(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);

<#if (X_FILEPARA2?trim)?split(' ')[0]=='String'>
        String ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getStringParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}","");
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='int'||(X_FILEPARA2?trim)?split(' ')[0]=='Integer' >
        int ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='long'||(X_FILEPARA2?trim)?split(' ')[0]=='Long' >
        long ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getLongParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='float'||(X_FILEPARA2?trim)?split(' ')[0]=='Float' >
        float ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='double'||(X_FILEPARA2?trim)?split(' ')[0]=='Double' >
        double ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#else>
        ${X_FILEPARA2?trim} = ServletUtils.objectMethod(${(X_FILEPARA2?trim)?split(' ')[0]}.class, request);
</#if>
        String status = ServletRequestUtils.getStringParameter(request, "status","");
        String note = ServletRequestUtils.getStringParameter(request, "note","");
        ResultVo result = ${fileid_cap_first}Verify.verifyID(${X_FILEPARA2?split(" ")[1]});
        if (result.isSuccess())
        {
            result = this.${fileid_uncap_first}Service.check(loginVo, ${X_FILEPARA2?split(" ")[1]}, status, note);
        }
        MisServletUtil.writeJsonToPage(result, response);
    }

</#if>
<#if (X_FILEPARA1?index_of("R")>-1)>
    /**
     * 审核
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=publish")
    public void publish(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);

<#if (X_FILEPARA2?trim)?split(' ')[0]=='String'>
        String ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getStringParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}","");
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='int'||(X_FILEPARA2?trim)?split(' ')[0]=='Integer' >
        int ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='long'||(X_FILEPARA2?trim)?split(' ')[0]=='Long' >
        long ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getLongParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='float'||(X_FILEPARA2?trim)?split(' ')[0]=='Float' >
        float ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='double'||(X_FILEPARA2?trim)?split(' ')[0]=='Double' >
        double ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#else>
        ${X_FILEPARA2?trim} = ServletUtils.objectMethod(${(X_FILEPARA2?trim)?split(' ')[0]}.class, request);
</#if>
        ResultVo result = ${fileid_cap_first}Verify.verifyID(${X_FILEPARA2?split(" ")[1]});
        if (result.isSuccess())
        {
            result = this.${fileid_uncap_first}Service.publish(loginVo, ${X_FILEPARA2?split(" ")[1]});
        }
        MisServletUtil.writeJsonToPage(result, response);
    }

</#if>
<#if (X_FILEPARA1?index_of("G")>-1)>
    /**
     * 详情
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "method=detail")
    public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        this.form(request, response, "D");
        return "${X_FILEPARA3}/${fileid_uncap}/${fileid_uncap}_detail";
    }

</#if>
<#if (X_FILEPARA1?index_of("G")>-1)||(X_FILEPARA1?index_of("U")>-1)||(X_FILEPARA1?index_of("C")>-1)>
    private void form(HttpServletRequest request, HttpServletResponse response, String formType) throws Exception
    {
        LoginVo loginVo = MisServletUtil.getLoginVo(request);
        Map<String, Object> result = new HashMap<String, Object>();
        boolean success = false;
        Object data = null;

        if (!"A".equals(formType))
        {
<#if (X_FILEPARA2?trim)?split(' ')[0]=='String'>
            String ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getStringParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}","");
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='int'||(X_FILEPARA2?trim)?split(' ')[0]=='Integer' >
            int ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='long'||(X_FILEPARA2?trim)?split(' ')[0]=='Long' >
            long ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getLongParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='float'||(X_FILEPARA2?trim)?split(' ')[0]=='Float' >
            float ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#elseif (X_FILEPARA2?trim)?split(' ')[0]=='double'||(X_FILEPARA2?trim)?split(' ')[0]=='Double' >
            double ${(X_FILEPARA2?trim)?split(' ')[1]} = ServletRequestUtils.getIntParameter(request, "${(X_FILEPARA2?trim)?split(' ')[1]}",0);
<#else>
            ${X_FILEPARA2?trim} = ServletUtils.objectMethod(${(X_FILEPARA2?trim)?split(' ')[0]}.class, request);
</#if>
            data = this.${fileid_uncap_first}Service.get${fileid_cap_first}(loginVo, ${X_FILEPARA2?split(" ")[1]});
        }
        success = true;
        result.put("success", success);
        result.put("data", data);
        result.put("formType", formType);
        ServletUtils.writeString(JsonUtils.toJson(result), response);
    }

</#if>
}