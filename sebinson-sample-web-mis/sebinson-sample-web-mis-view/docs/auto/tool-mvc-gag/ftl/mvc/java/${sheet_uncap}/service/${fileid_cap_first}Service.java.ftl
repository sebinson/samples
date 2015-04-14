package ${sheet}.service;

import java.util.List;

<#if (X_FILEPARA1?index_of("P")>-1)>
import com.gooagoo.base.mis.common.vo.PageCondition;
import com.gooagoo.base.mis.common.vo.PageModel;
</#if>
import com.gooagoo.base.mis.common.vo.ResultVo;
import com.gooagoo.base.mis.common.vo.LoginVo;
import ${sheet}.vo.F${fileid_cap_first};

public interface ${fileid_cap_first}Service
{
<#if (X_FILEPARA1?index_of("A")>-1)>
    /**
     * 添加
     * @param loginVo
     * @param ${fileid_uncap_first}
     * @return
     * @throws Exception
     */
    public ResultVo add(LoginVo loginVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("U")>-1)>
    /**
     * 修改
     * @param loginVo
     * @param ${fileid_uncap_first}
     * @return
     * @throws Exception
     */
    public ResultVo update(LoginVo loginVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("D")>-1)>
    /**
     * 删除
     * @param loginVo
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @return
     */
    public ResultVo delete(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("C")>-1)>
    /**
     * 审核
     * @param loginVo
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @param status
     * @param note
     * @return
     */
    public ResultVo check(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]}, String status, String note) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("R")>-1)>
    /**
     * 发布
     * @param loginVo
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @return
     */
    public ResultVo publish(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("G")>-1)||(X_FILEPARA1?index_of("U")>-1)||(X_FILEPARA1?index_of("C")>-1)>
    /**
     * 获取详情
     * @param loginVo
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @return
     * @throws Exception
     */
    public F${fileid_cap_first} get${fileid_cap_first}(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("L")>-1)>
    /**
     * 获取列表
     * @param loginVo
     * @param ${fileid_uncap_first}
     * @return
     * @throws Exception
     */
    public List<F${fileid_cap_first}> list${fileid_cap_first}(LoginVo loginVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("P")>-1)>
    /**
     * 分页获取列表
     * @param loginVo
     * @param ${fileid_uncap_first}
     * @return
     * @throws Exception
     */
    public PageModel<F${fileid_cap_first}> page${fileid_cap_first}(LoginVo loginVo, F${fileid_cap_first} ${fileid_uncap_first}, PageCondition condition) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("K")>-1)||(X_FILEPARA1?index_of("N")>-1)>
	/**
     * 锁定/解锁
     * @param loginVo
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @return
     * @throws Exception
     */
    public ResultVo lock${fileid_cap_first}(LoginVo loginVo, String ${X_FILEPARA2?split(" ")[1]}, String lockType) throws Exception;

</#if>
}
