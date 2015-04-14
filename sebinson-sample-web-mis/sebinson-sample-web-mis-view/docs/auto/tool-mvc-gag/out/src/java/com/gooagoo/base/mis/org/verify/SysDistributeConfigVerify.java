package com.gooagoo.base.mis.org.verify;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.gooagoo.base.mis.common.utils.MisServletUtil;
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.org.vo.FSysDistributeConfig;

public class SysDistributeConfigVerify
{
    /**
     * 校验主键
     * @param id
     * @return
     */
    public static ResultVo verifyID(String id)
    {
        ResultVo result = new ResultVo(true, "操作成功");
        if (!StringUtils.hasText(id))
        {
            result.initResultVo(false, "id不能为空");
        }
        return result;
    }

    /**
     * 校验表单数据
     * @param card
     * @return
     */
    public static ResultVo verifyForm(FSysDistributeConfig sysDistributeConfig)
    {
        String partnerId = sysDistributeConfig.getPartnerId();
        if (!StringUtils.hasText(partnerId))
        {
            return new ResultVo(false, "partnerId不能为空");
        }
        if (MisServletUtil.getTextLength(partnerId) > 32)
        {
            return new ResultVo(false, "partnerId过长");
        }
        String isDel = sysDistributeConfig.getIsDel();
        if (!StringUtils.hasText(isDel))
        {
            return new ResultVo(false, "isDel不能为空");
        }
        if (MisServletUtil.getTextLength(isDel) > 1)
        {
            return new ResultVo(false, "isDel过长");
        }
        Date createTime = sysDistributeConfig.getCreateTime();
        if (createTime == null)
        {
            return new ResultVo(false, "createTime不能为空");
        }

        return new ResultVo(true, "操作成功");
    }
}