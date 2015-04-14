package ${sheet}.service;

import java.util.List;

<#if (X_FILEPARA1?index_of("P")>-1)>
import com.gooagoo.shop.common.vo.PageCondition;
import com.gooagoo.shop.common.vo.PageModel;
</#if>
import com.gooagoo.shop.common.vo.ResultVo;
import com.gooagoo.shop.common.vo.ShopVo;
import ${sheet}.vo.F${fileid_cap_first};

public interface ${fileid_cap_first}Service
{
<#if (X_FILEPARA1?index_of("A")>-1)>
    /**
     * 添加
     * @param shopVo
     * @param ${fileid_uncap_first}
     * @return
     * @throws Exception
     */
    public ResultVo add(ShopVo shopVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("U")>-1)>
    /**
     * 修改
     * @param shopVo
     * @param ${fileid_uncap_first}
     * @return
     * @throws Exception
     */
    public ResultVo update(ShopVo shopVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("D")>-1)>
    /**
     * 删除
     * @param shopVo
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @return
     */
    public ResultVo delete(ShopVo shopVo, String ${X_FILEPARA2?split(" ")[1]}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("C")>-1)>
    /**
     * 审核
     * @param shopVo
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @param status
     * @param note
     * @return
     */
    public ResultVo check(ShopVo shopVo, String ${X_FILEPARA2?split(" ")[1]}, String status, String note) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("R")>-1)>
    /**
     * 发布
     * @param shopVo
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @return
     */
    public ResultVo publish(ShopVo shopVo, String ${X_FILEPARA2?split(" ")[1]}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("G")>-1)||(X_FILEPARA1?index_of("U")>-1)||(X_FILEPARA1?index_of("C")>-1)>
    /**
     * 获取详情
     * @param shopVo
     * @param ${X_FILEPARA2?split(" ")[1]}
     * @return
     * @throws Exception
     */
    public F${fileid_cap_first} get${fileid_cap_first}(ShopVo shopVo, String ${X_FILEPARA2?split(" ")[1]}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("L")>-1)>
    /**
     * 获取列表
     * @param shopVo
     * @param ${fileid_uncap_first}
     * @return
     * @throws Exception
     */
    public List<F${fileid_cap_first}> list${fileid_cap_first}(ShopVo shopVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception;

</#if>
<#if (X_FILEPARA1?index_of("P")>-1)>
    /**
     * 分页获取列表
     * @param shopVo
     * @param ${fileid_uncap_first}
     * @return
     * @throws Exception
     */
    public PageModel<F${fileid_cap_first}> page${fileid_cap_first}(ShopVo shopVo, F${fileid_cap_first} ${fileid_uncap_first}, PageCondition condition) throws Exception;

</#if>
}
