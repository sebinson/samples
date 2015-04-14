package com.gooagoo.base.mis.device.service;

import java.util.List;

import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.common.vo.LoginVo;
import com.gooagoo.base.mis.device.vo.FAnalyzeTemplate;

public interface AnalyzeTemplateService
{
    /**
     * 添加
     * @param loginVo
     * @param analyzeTemplate
     * @return
     * @throws Exception
     */
    public ResultVo add(LoginVo loginVo, FAnalyzeTemplate analyzeTemplate) throws Exception;

    /**
     * 修改
     * @param loginVo
     * @param analyzeTemplate
     * @return
     * @throws Exception
     */
    public ResultVo update(LoginVo loginVo, FAnalyzeTemplate analyzeTemplate) throws Exception;

    /**
     * 删除
     * @param loginVo
     * @param analyzeTemplateId
     * @return
     */
    public ResultVo delete(LoginVo loginVo, String analyzeTemplateId) throws Exception;

    /**
     * 获取详情
     * @param loginVo
     * @param analyzeTemplateId
     * @return
     * @throws Exception
     */
    public FAnalyzeTemplate getAnalyzeTemplate(LoginVo loginVo, String analyzeTemplateId) throws Exception;

    /**
     * 获取列表
     * @param loginVo
     * @param analyzeTemplate
     * @return
     * @throws Exception
     */
    public List<FAnalyzeTemplate> listAnalyzeTemplate(LoginVo loginVo, FAnalyzeTemplate analyzeTemplate) throws Exception;

    /**
     * 分页获取列表
     * @param loginVo
     * @param analyzeTemplate
     * @return
     * @throws Exception
     */
    public PageModel<FAnalyzeTemplate> pageAnalyzeTemplate(LoginVo loginVo, FAnalyzeTemplate analyzeTemplate, PageCondition condition) throws Exception;

}
