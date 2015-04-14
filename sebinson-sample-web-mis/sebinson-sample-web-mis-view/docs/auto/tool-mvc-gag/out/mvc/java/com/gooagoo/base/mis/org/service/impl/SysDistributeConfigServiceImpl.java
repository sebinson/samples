package com.gooagoo.base.mis.org.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gooagoo.base.manager.sys.api.read.generator.SysDistributeConfigGeneratorReadService;
import com.gooagoo.base.manager.sys.api.write.generator.SysDistributeConfigGeneratorWriteService;
import com.gooagoo.base.manager.sys.entity.generator.SysDistributeConfig;
import com.gooagoo.base.manager.sys.entity.generator.SysDistributeConfigExample;
import com.gooagoo.base.manager.sys.entity.generator.SysDistributeConfigExample.Criteria;
import com.gooagoo.base.mis.common.vo.LoginVo;
import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.org.service.SysDistributeConfigService;
import com.gooagoo.base.mis.org.vo.FSysDistributeConfig;
import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.common.vo.LoginVo;

@Service(value = "SysDistributeConfigService")
public class SysDistributeConfigServiceImpl implements SysDistributeConfigService
{
    @Autowired
    private SysDistributeConfigGeneratorReadService sysDistributeConfigGeneratorReadService;
    @Autowired
    private SysDistributeConfigGeneratorWriteService sysDistributeConfigGeneratorWriteService;

    @Override
    public ResultVo add(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig) throws Exception
    {
        ResultVo resultVo = new ResultVo(false, "操作失败");
        SysDistributeConfig ssysDistributeConfig = new SysDistributeConfig();
        BeanUtils.copyProperties(sysDistributeConfig,ssysDistributeConfig);
        ssysDistributeConfig.setCreateTime(new Date());
        boolean result = this.sysDistributeConfigGeneratorWriteService.insertSelective(ssysDistributeConfig);
        if (result)
        {
            resultVo.setSuccess(true);
            resultVo.setCode("操作成功");
        }
        return resultVo;
    }

    @Override
    public ResultVo update(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig) throws Exception
    {
        ResultVo resultVo = new ResultVo(false, "操作失败");
        SysDistributeConfig ssysDistributeConfig = new SysDistributeConfig();
        BeanUtils.copyProperties(sysDistributeConfig, ssysDistributeConfig);
        boolean result = this.sysDistributeConfigGeneratorWriteService.updateByPrimaryKeySelective(ssysDistributeConfig);
        if (result)
        {
            resultVo.setSuccess(true);
            resultVo.setCode("操作成功");
        }
        return resultVo;
    }

    @Override
    public ResultVo delete(LoginVo loginVo, String id)
    {
        ResultVo resultVo = new ResultVo(false, "操作失败");
        boolean result = this.sysDistributeConfigGeneratorWriteService.deleteByPrimaryKey(id);
        if (result)
        {
            resultVo.setSuccess(true);
            resultVo.setCode("操作成功");
        }
        return resultVo;
    }

    @Override
    public FSysDistributeConfig getSysDistributeConfig(LoginVo loginVo, String id) throws Exception
    {
        FSysDistributeConfig fSysDistributeConfig = new FSysDistributeConfig();
        SysDistributeConfig sysDistributeConfig = this.sysDistributeConfigGeneratorReadService.selectByPrimaryKey(id);
        BeanUtils.copyProperties(sysDistributeConfig, fSysDistributeConfig);
        return fSysDistributeConfig;
    }

    @Override
    public List<FSysDistributeConfig> listSysDistributeConfig(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig) throws Exception
    {
        SysDistributeConfigExample sysDistributeConfigExample = new SysDistributeConfigExample();
        Criteria createCriteria = sysDistributeConfigExample.createCriteria();
        List<SysDistributeConfig> sysDistributeConfigList = this.sysDistributeConfigGeneratorReadService.selectByExample(sysDistributeConfigExample);
        List<FSysDistributeConfig> fsysDistributeConfigList = new ArrayList<FSysDistributeConfig>();
        FSysDistributeConfig fsysDistributeConfig = null;
        for (SysDistributeConfig ssysDistributeConfig : sysDistributeConfigList)
        {
            fsysDistributeConfig = new FSysDistributeConfig();
            BeanUtils.copyProperties(ssysDistributeConfig, fsysDistributeConfig);
            fsysDistributeConfigList.add(fsysDistributeConfig);
        }
        return fsysDistributeConfigList;
    }

    @Override
    public PageModel<FSysDistributeConfig> pageSysDistributeConfig(LoginVo loginVo, FSysDistributeConfig sysDistributeConfig, PageCondition condition) throws Exception
    {
        PageModel<FSysDistributeConfig> pm = new PageModel<FSysDistributeConfig>();
        pm.setPageIndex(condition.getPageIndex());
        pm.setPageSize(condition.getPageSize());
        SysDistributeConfigExample sysDistributeConfigExample = new SysDistributeConfigExample();
        Criteria createCriteria = sysDistributeConfigExample.createCriteria();
        Integer count = this.sysDistributeConfigGeneratorReadService.countByExample(sysDistributeConfigExample);
        if (count > 0)
        {
            pm.setTotal(count);
            sysDistributeConfigExample.setPage(condition.getPageIndex(), condition.getPageSize());
            List<SysDistributeConfig> sysDistributeConfigList = this.sysDistributeConfigGeneratorReadService.selectByExample(sysDistributeConfigExample);
            FSysDistributeConfig fSysDistributeConfig = null;
            for (SysDistributeConfig ssysDistributeConfig : sysDistributeConfigList)
            {
                fSysDistributeConfig = new FSysDistributeConfig();
                BeanUtils.copyProperties(ssysDistributeConfig, fSysDistributeConfig);
                pm.getRows().add(fSysDistributeConfig);
            }
        }
        return pm;
    }
 
}