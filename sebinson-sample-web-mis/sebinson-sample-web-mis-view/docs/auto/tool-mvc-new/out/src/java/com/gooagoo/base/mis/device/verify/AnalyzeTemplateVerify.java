package com.gooagoo.base.mis.device.verify;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.gooagoo.base.mis.common.utils.MisServletUtil;
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.device.vo.FAnalyzeTemplate;

public class AnalyzeTemplateVerify
{
    /**
     * 校验主键
     * @param analyzeTemplateId
     * @return
     */
    public static ResultVo verifyID(String analyzeTemplateId)
    {
        ResultVo result = new ResultVo(true, "操作成功");
        if (!StringUtils.hasText(analyzeTemplateId))
        {
            result.initResultVo(false, "analyzeTemplateId不能为空");
        }
        return result;
    }

    /**
     * 校验表单数据
     * @param card
     * @return
     */
    public static ResultVo verifyForm(FAnalyzeTemplate analyzeTemplate)
    {
        String type = analyzeTemplate.getType();
        if (!StringUtils.hasText(type))
        {
            return new ResultVo(false, "type不能为空");
        }
        if (MisServletUtil.getTextLength(type) > 2)
        {
            return new ResultVo(false, "type过长");
        }
        String name = analyzeTemplate.getName();
        if (!StringUtils.hasText(name))
        {
            return new ResultVo(false, "name不能为空");
        }
        if (MisServletUtil.getTextLength(name) > 32)
        {
            return new ResultVo(false, "name过长");
        }
        String url = analyzeTemplate.getUrl();
        if (!StringUtils.hasText(url))
        {
            return new ResultVo(false, "url不能为空");
        }
        if (MisServletUtil.getTextLength(url) > 255)
        {
            return new ResultVo(false, "url过长");
        }
        String status = analyzeTemplate.getStatus();
        if (!StringUtils.hasText(status))
        {
            return new ResultVo(false, "status不能为空");
        }
        if (MisServletUtil.getTextLength(status) > 1)
        {
            return new ResultVo(false, "status过长");
        }
        String isDel = analyzeTemplate.getIsDel();
        if (!StringUtils.hasText(isDel))
        {
            return new ResultVo(false, "isDel不能为空");
        }
        if (MisServletUtil.getTextLength(isDel) > 1)
        {
            return new ResultVo(false, "isDel过长");
        }
        Date createTime = analyzeTemplate.getCreateTime();
        if (createTime == null)
        {
            return new ResultVo(false, "createTime不能为空");
        }
        Date cTimeStamp = analyzeTemplate.getCTimeStamp();
        if (cTimeStamp == null)
        {
            return new ResultVo(false, "cTimeStamp不能为空");
        }

        return new ResultVo(true, "操作成功");
    }
}