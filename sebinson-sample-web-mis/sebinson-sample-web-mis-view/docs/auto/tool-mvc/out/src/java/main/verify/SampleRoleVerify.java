package com.gooagoo.sample.verify;

import org.springframework.util.StringUtils;

import com.gooagoo.shop.common.utils.ShopServletUtil;
import com.gooagoo.shop.common.vo.ResultVo;
import com.gooagoo.sample.vo.FSampleRole;

public class SampleRoleVerify
{
    /**
     * 校验主键
     * @param roleId
     * @return
     */
    public static ResultVo verifyID(String roleId)
    {
        ResultVo result = new ResultVo(true, "操作成功");
        if (!StringUtils.hasText(roleId))
        {
            result.initResultVo(false, "roleId不能为空");
        }
        return result;
    }

    /**
     * 校验表单数据
     * @param card
     * @return
     */
    public static ResultVo verifyForm(FSampleRole sampleRole)
    {
        String roleCode = sampleRole.getRoleCode();
        if (!StringUtils.hasText(roleCode))
        {
            return new ResultVo(false, "roleCode不能为空");
        }
        if (ShopServletUtil.getTextLength(roleCode) > 32)
        {
            return new ResultVo(false, "roleCode过长");
        }
        String roleName = sampleRole.getRoleName();
        if (!StringUtils.hasText(roleName))
        {
            return new ResultVo(false, "roleName不能为空");
        }
        if (ShopServletUtil.getTextLength(roleName) > 32)
        {
            return new ResultVo(false, "roleName过长");
        }

        return new ResultVo(true, "操作成功");
    }
}