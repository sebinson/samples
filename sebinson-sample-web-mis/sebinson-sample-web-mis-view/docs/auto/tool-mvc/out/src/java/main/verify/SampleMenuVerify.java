package com.gooagoo.sample.verify;

import org.springframework.util.StringUtils;

import com.gooagoo.shop.common.utils.ShopServletUtil;
import com.gooagoo.shop.common.vo.ResultVo;
import com.gooagoo.sample.vo.FSampleMenu;

public class SampleMenuVerify
{
    /**
     * 校验主键
     * @param menuId
     * @return
     */
    public static ResultVo verifyID(String menuId)
    {
        ResultVo result = new ResultVo(true, "操作成功");
        if (!StringUtils.hasText(menuId))
        {
            result.initResultVo(false, "menuId不能为空");
        }
        return result;
    }

    /**
     * 校验表单数据
     * @param card
     * @return
     */
    public static ResultVo verifyForm(FSampleMenu sampleMenu)
    {
        String menuCode = sampleMenu.getMenuCode();
        if (!StringUtils.hasText(menuCode))
        {
            return new ResultVo(false, "menuCode不能为空");
        }
        if (ShopServletUtil.getTextLength(menuCode) > 32)
        {
            return new ResultVo(false, "menuCode过长");
        }
        String menuType = sampleMenu.getMenuType();
        if (!StringUtils.hasText(menuType))
        {
            return new ResultVo(false, "menuType不能为空");
        }
        if (ShopServletUtil.getTextLength(menuType) > 2)
        {
            return new ResultVo(false, "menuType过长");
        }
        String menuName = sampleMenu.getMenuName();
        if (!StringUtils.hasText(menuName))
        {
            return new ResultVo(false, "menuName不能为空");
        }
        if (ShopServletUtil.getTextLength(menuName) > 32)
        {
            return new ResultVo(false, "menuName过长");
        }
        String menuStatus = sampleMenu.getMenuStatus();
        if (!StringUtils.hasText(menuStatus))
        {
            return new ResultVo(false, "menuStatus不能为空");
        }
        if (ShopServletUtil.getTextLength(menuStatus) > 1)
        {
            return new ResultVo(false, "menuStatus过长");
        }

        return new ResultVo(true, "操作成功");
    }
}