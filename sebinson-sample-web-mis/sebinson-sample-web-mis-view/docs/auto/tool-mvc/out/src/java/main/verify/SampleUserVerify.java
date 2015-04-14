package com.gooagoo.sample.verify;

import org.springframework.util.StringUtils;

import com.gooagoo.shop.common.utils.ShopServletUtil;
import com.gooagoo.shop.common.vo.ResultVo;
import com.gooagoo.sample.vo.FSampleUser;

public class SampleUserVerify
{
    /**
     * 校验主键
     * @param userId
     * @return
     */
    public static ResultVo verifyID(String userId)
    {
        ResultVo result = new ResultVo(true, "操作成功");
        if (!StringUtils.hasText(userId))
        {
            result.initResultVo(false, "userId不能为空");
        }
        return result;
    }

    /**
     * 校验表单数据
     * @param card
     * @return
     */
    public static ResultVo verifyForm(FSampleUser sampleUser)
    {
        String userName = sampleUser.getUserName();
        if (!StringUtils.hasText(userName))
        {
            return new ResultVo(false, "userName不能为空");
        }
        if (ShopServletUtil.getTextLength(userName) > 32)
        {
            return new ResultVo(false, "userName过长");
        }
        String password = sampleUser.getPassword();
        if (!StringUtils.hasText(password))
        {
            return new ResultVo(false, "password不能为空");
        }
        if (ShopServletUtil.getTextLength(password) > 32)
        {
            return new ResultVo(false, "password过长");
        }

        return new ResultVo(true, "操作成功");
    }
}