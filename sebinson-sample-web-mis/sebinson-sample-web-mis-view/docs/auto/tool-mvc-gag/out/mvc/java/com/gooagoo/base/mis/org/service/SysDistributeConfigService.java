package com.gooagoo.base.mis.org.service;

import java.util.List;

import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.common.vo.LoginVo;
import com.gooagoo.base.mis.org.vo.FSysDistributeConfig;

public interface SysDistributeConfigService
{
    /**
     * 添加
     * @param loginVo
     * @param sysDistributeConfig
     * @return
     * @throws Exception
     */
    public ResultVo add(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig) throws Exception;

    /**
     * 修改
     * @param loginVo
     * @param sysDistributeConfig
     * @return
     * @throws Exception
     */
    public ResultVo update(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig) throws Exception;

    /**
     * 删除
     * @param loginVo
     * @param id
     * @return
     */
    public ResultVo delete(LoginVo loginVo, String id) throws Exception;

    /**
     * 获取详情
     * @param loginVo
     * @param id
     * @return
     * @throws Exception
     */
    public FSysDistributeConfig getSysDistributeConfig(LoginVo loginVo, String id) throws Exception;

    /**
     * 获取列表
     * @param loginVo
     * @param sysDistributeConfig
     * @return
     * @throws Exception
     */
    public List<FSysDistributeConfig> listSysDistributeConfig(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig) throws Exception;

    /**
     * 分页获取列表
     * @param loginVo
     * @param sysDistributeConfig
     * @return
     * @throws Exception
     */
    public PageModel<FSysDistributeConfig> pageSysDistributeConfig(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig, PageCondition condition) throws Exception;

}
