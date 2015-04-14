package ${sheet_uncap}.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gooagoo.base.manager.sys.api.read.generator.${fileid_cap_first}GeneratorReadService;
import com.gooagoo.base.manager.sys.api.write.generator.${fileid_cap_first}GeneratorWriteService;
import com.gooagoo.base.manager.sys.entity.generator.${fileid_cap_first};
import com.gooagoo.base.manager.sys.entity.generator.${fileid_cap_first}Example;
import com.gooagoo.base.manager.sys.entity.generator.${fileid_cap_first}Example.Criteria;
import com.gooagoo.base.mis.common.vo.LoginVo;
import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
import com.gooagoo.base.mis.common.vo.ResultVo;
import ${sheet_uncap}.service.${fileid_cap_first}Service;
import ${sheet_uncap}.vo.F${fileid_cap_first};
<#if (X_FILEPARA1?index_of("P")>-1)>
import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
</#if>
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.common.vo.LoginVo;

@Service(value = "${fileid_cap_first}Service")
public class ${fileid_cap_first}ServiceImpl implements ${fileid_cap_first}Service
{
    @Autowired
    private ${fileid_cap_first}GeneratorReadService ${fileid_uncap_first}GeneratorReadService;
    @Autowired
    private ${fileid_cap_first}GeneratorWriteService ${fileid_uncap_first}GeneratorWriteService;

<#if (X_FILEPARA1?index_of("A")>-1)>
    @Override
    public ResultVo add(LoginVo loginVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception
    {
        ResultVo resultVo = new ResultVo(false, "操作失败");
        ${fileid_cap_first} s${fileid_uncap_first} = new ${fileid_cap_first}();
        BeanUtils.copyProperties(${fileid_uncap_first},s${fileid_uncap_first});
        s${fileid_uncap_first}.setCreateTime(new Date());
        boolean result = this.${fileid_uncap_first}GeneratorWriteService.insertSelective(s${fileid_uncap_first});
        if (result)
        {
            resultVo.setSuccess(true);
            resultVo.setCode("操作成功");
        }
        return resultVo;
    }

</#if>
<#if (X_FILEPARA1?index_of("U")>-1)>
    @Override
    public ResultVo update(LoginVo loginVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception
    {
        ResultVo resultVo = new ResultVo(false, "操作失败");
        ${fileid_cap_first} s${fileid_uncap_first} = new ${fileid_cap_first}();
        BeanUtils.copyProperties(${fileid_uncap_first}, s${fileid_uncap_first});
        boolean result = this.${fileid_uncap_first}GeneratorWriteService.updateByPrimaryKeySelective(s${fileid_uncap_first});
        if (result)
        {
            resultVo.setSuccess(true);
            resultVo.setCode("操作成功");
        }
        return resultVo;
    }

</#if>
<#if (X_FILEPARA1?index_of("D")>-1)>
    @Override
    public ResultVo delete(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]})
    {
        ResultVo resultVo = new ResultVo(false, "操作失败");
        boolean result = this.${fileid_uncap_first}GeneratorWriteService.deleteByPrimaryKey(${X_FILEPARA2?split(" ")[1]});
        if (result)
        {
            resultVo.setSuccess(true);
            resultVo.setCode("操作成功");
        }
        return resultVo;
    }

</#if>
<#if (X_FILEPARA1?index_of("C")>-1)>
    @Override
    public ResultVo check(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]}, String status, String note)
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("${X_FILEPARA2?split(" ")[1]}:" + ${X_FILEPARA2?split(" ")[1]});
        System.out.println("status:" + status);
        System.out.println("note:" + note);
        return new ResultVo(false, "还在维护中...");
    }

</#if>
<#if (X_FILEPARA1?index_of("R")>-1)>
    @Override
    public ResultVo publish(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]})
    {
        // TODO Auto-generated method stub
        System.out.println("loginVo:" + this.gson.toJson(loginVo));
        System.out.println("${X_FILEPARA2?split(" ")[1]}:" + ${X_FILEPARA2?split(" ")[1]});
        return new ResultVo(false, "还在维护中...");
    }

</#if>
<#if (X_FILEPARA1?index_of("G")>-1)||(X_FILEPARA1?index_of("U")>-1)||(X_FILEPARA1?index_of("C")>-1)>
    @Override
    public F${fileid_cap_first} get${fileid_cap_first}(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]}) throws Exception
    {
        F${fileid_cap_first} f${fileid_cap_first} = new F${fileid_cap_first}();
        ${fileid_cap_first} ${fileid_uncap_first} = this.${fileid_uncap_first}GeneratorReadService.selectByPrimaryKey(${X_FILEPARA2?split(" ")[1]});
        BeanUtils.copyProperties(${fileid_uncap_first}, f${fileid_cap_first});
        return f${fileid_cap_first};
    }

</#if>
<#if (X_FILEPARA1?index_of("L")>-1)>
    @Override
    public List<F${fileid_cap_first}> list${fileid_cap_first}(LoginVo loginVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception
    {
        ${fileid_cap_first}Example ${fileid_uncap_first}Example = new ${fileid_cap_first}Example();
        Criteria createCriteria = ${fileid_uncap_first}Example.createCriteria();
        List<${fileid_cap_first}> ${fileid_uncap_first}List = this.${fileid_uncap_first}GeneratorReadService.selectByExample(${fileid_uncap_first}Example);
        List<F${fileid_cap_first}> f${fileid_uncap_first}List = new ArrayList<F${fileid_cap_first}>();
        F${fileid_cap_first} f${fileid_uncap_first} = null;
        for (${fileid_cap_first} s${fileid_uncap_first} : ${fileid_uncap_first}List)
        {
            f${fileid_uncap_first} = new F${fileid_cap_first}();
            BeanUtils.copyProperties(s${fileid_uncap_first}, f${fileid_uncap_first});
            f${fileid_uncap_first}List.add(f${fileid_uncap_first});
        }
        return f${fileid_uncap_first}List;
    }

</#if>
<#if (X_FILEPARA1?index_of("P")>-1)>
    @Override
    public PageModel<F${fileid_cap_first}> page${fileid_cap_first}(LoginVo loginVo, F${fileid_cap_first} ${fileid_uncap_first}, PageCondition condition) throws Exception
    {
        PageModel<F${fileid_cap_first}> pm = new PageModel<F${fileid_cap_first}>();
        pm.setPageIndex(condition.getPageIndex());
        pm.setPageSize(condition.getPageSize());
        ${fileid_cap_first}Example ${fileid_uncap_first}Example = new ${fileid_cap_first}Example();
        Criteria createCriteria = ${fileid_uncap_first}Example.createCriteria();
        Integer count = this.${fileid_uncap_first}GeneratorReadService.countByExample(${fileid_uncap_first}Example);
        if (count > 0)
        {
            pm.setTotal(count);
            ${fileid_uncap_first}Example.setPage(condition.getPageIndex(), condition.getPageSize());
            List<${fileid_cap_first}> ${fileid_uncap_first}List = this.${fileid_uncap_first}GeneratorReadService.selectByExample(${fileid_uncap_first}Example);
            F${fileid_cap_first} f${fileid_cap_first} = null;
            for (${fileid_cap_first} s${fileid_uncap_first} : ${fileid_uncap_first}List)
            {
                f${fileid_cap_first} = new F${fileid_cap_first}();
                BeanUtils.copyProperties(s${fileid_uncap_first}, f${fileid_cap_first});
                pm.getRows().add(f${fileid_cap_first});
            }
        }
        return pm;
    }
 
</#if>
<#if (X_FILEPARA1?index_of("K")>-1)||(X_FILEPARA1?index_of("N")>-1)>
	@Override
    public ResultVo lock${fileid_cap_first}(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]}, String lockType) throws Exception
    {
        ResultVo resultVo = new ResultVo(false, "操作失败!");
        ${fileid_cap_first} ${fileid_uncap_first} = new ${fileid_cap_first}();
        <#list list as column>
	        <#if column.x_ckey?exists && column.x_ckey=='PRI'>
	          ${fileid_uncap_first}.set${column.x_cname?cap_first}(${X_FILEPARA2?split(" ")[1]});
	        </#if>
	        <#if column.x_cname?exists && column.x_cname?ends_with("Status")>
	          ${fileid_uncap_first}.set${column.x_cname?cap_first}(lockType);
	        </#if>
        </#list>
        boolean result = this.${fileid_uncap_first}GeneratorWriteService.updateByPrimaryKeySelective(${fileid_uncap_first});
        if (result)
        {
            resultVo.setSuccess(true);
            resultVo.setCode("操作成功!");
        }
        return resultVo;
    }
	
</#if>
}