package ${sheet}.verify;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.gooagoo.base.mis.common.utils.MisServletUtil;
import com.gooagoo.base.mis.common.vo.ResultVo;
import ${sheet}.vo.F${fileid_cap_first};

public class ${fileid_cap_first}Verify
{
    /**
     * 校验主键
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @return
     */
    public static ResultVo verifyID(String ${X_FILEPARA2?split(" ")[1]})
    {
        ResultVo result = new ResultVo(true, "操作成功");
        if (!StringUtils.hasText(${X_FILEPARA2?split(" ")[1]}))
        {
            result.initResultVo(false, "${X_FILEPARA2?split(" ")[1]}不能为空");
        }
        return result;
    }

    /**
     * 校验表单数据
     * @param card
     * @return
     */
    public static ResultVo verifyForm(F${fileid_cap_first} ${fileid_uncap_first})
    {
<#list list as column>
        <#if column.x_cisNullable=='N'&& column.x_showInForm=='Y'&& column.x_ckey!='PRI'>
        ${column.x_ctype} ${column.x_cname} = ${fileid_uncap_first}.get${column.x_cname?cap_first}();
  <#if column.x_ctype=='String'>
        if (!StringUtils.hasText(${column.x_cname}))
        {
            return new ResultVo(false, "${column.x_cname}不能为空");
        }
    <#if column.x_clength!="0" && column.x_clength!="">
        if (MisServletUtil.getTextLength(${column.x_cname}) > ${column.x_clength})
        {
            return new ResultVo(false, "${column.x_cname}过长");
        }
    </#if>
  <#else>
        if (${column.x_cname} == null)
        {
            return new ResultVo(false, "${column.x_cname}不能为空");
        }
  </#if>
</#if>
</#list>

        return new ResultVo(true, "操作成功");
    }
}