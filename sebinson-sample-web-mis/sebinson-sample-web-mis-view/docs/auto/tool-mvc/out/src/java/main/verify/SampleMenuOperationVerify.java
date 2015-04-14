package com.gooagoo.sample.verify;

import org.springframework.util.StringUtils;

import com.gooagoo.shop.common.utils.ShopServletUtil;
import com.gooagoo.shop.common.vo.ResultVo;
import com.gooagoo.sample.vo.FSampleMenuOperation;

public class SampleMenuOperationVerify
{
    /**
     * 校验主键
     * @param operId
     * @return
     */
    public static ResultVo verifyID(String operId)
    {
        ResultVo result = new ResultVo(true, "操作成功");
        if (!StringUtils.hasText(operId))
        {
            result.initResultVo(false, "operId不能为空");
        }
        return result;
    }

    /**
     * 校验表单数据
     * @param card
     * @return
     */
    public static ResultVo verifyForm(FSampleMenuOperation sampleMenuOperation)
    {
        String menuId = sampleMenuOperation.getMenuId();
        if (!StringUtils.hasText(menuId))
        {
            return new ResultVo(false, "menuId不能为空");
        }
        if (ShopServletUtil.getTextLength(menuId) > 32)
        {
            return new ResultVo(false, "menuId过长");
        }

        return new ResultVo(true, "操作成功");
    }
}