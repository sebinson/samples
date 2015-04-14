package ${sheet_uncap}.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ${sheet_uncap}.service.${fileid_cap_first}Service;
import ${sheet_uncap}.vo.F${fileid_cap_first};
<#if (X_FILEPARA1?index_of("P")>-1)>
import com.gooagoo.shop.common.vo.PageCondition;
import com.gooagoo.shop.common.vo.PageModel;
</#if>
import com.gooagoo.shop.common.vo.ResultVo;
import com.gooagoo.shop.common.vo.ShopVo;
import com.google.gson.Gson;

@Service(value = "${fileid_cap_first}Service")
public class ${fileid_cap_first}ServiceImpl implements ${fileid_cap_first}Service
{
    private final Gson gson = new Gson();

<#if (X_FILEPARA1?index_of("A")>-1)>
    @Override
    public ResultVo add(ShopVo shopVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("${fileid_cap_first}:" + this.gson.toJson(${fileid_uncap_first}));
        return new ResultVo(false, "还在维护中...");
    }

</#if>
<#if (X_FILEPARA1?index_of("U")>-1)>
    @Override
    public ResultVo update(ShopVo shopVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("${fileid_cap_first}:" + this.gson.toJson(${fileid_uncap_first}));
        return new ResultVo(false, "还在维护中...");
    }

</#if>
<#if (X_FILEPARA1?index_of("D")>-1)>
    @Override
    public ResultVo delete(ShopVo shopVo, String ${X_FILEPARA2?split(" ")[1]})
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("${X_FILEPARA2?split(" ")[1]}:" + ${X_FILEPARA2?split(" ")[1]});
        return new ResultVo(false, "还在维护中...");
    }

</#if>
<#if (X_FILEPARA1?index_of("C")>-1)>
    @Override
    public ResultVo check(ShopVo shopVo, String ${X_FILEPARA2?split(" ")[1]}, String status, String note)
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("${X_FILEPARA2?split(" ")[1]}:" + ${X_FILEPARA2?split(" ")[1]});
        System.out.println("status:" + status);
        System.out.println("note:" + note);
        return new ResultVo(false, "还在维护中...");
    }

</#if>
<#if (X_FILEPARA1?index_of("R")>-1)>
    @Override
    public ResultVo publish(ShopVo shopVo, String ${X_FILEPARA2?split(" ")[1]})
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("${X_FILEPARA2?split(" ")[1]}:" + ${X_FILEPARA2?split(" ")[1]});
        return new ResultVo(false, "还在维护中...");
    }

</#if>
<#if (X_FILEPARA1?index_of("G")>-1)||(X_FILEPARA1?index_of("U")>-1)||(X_FILEPARA1?index_of("C")>-1)>
    @Override
    public F${fileid_cap_first} get${fileid_cap_first}(ShopVo shopVo, String ${X_FILEPARA2?split(" ")[1]}) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("${X_FILEPARA2?split(" ")[1]}:" + ${X_FILEPARA2?split(" ")[1]});
        F${fileid_cap_first} ${fileid_uncap_first} = new F${fileid_cap_first}();
        ${fileid_uncap_first}.setCardId(${X_FILEPARA2?split(" ")[1]});
<#list list as column>
<#if column.x_ctype=='String' && column.x_showInForm=='Y'&& column.x_ckey!='PRI'>
        ${fileid_uncap_first}.set${column.x_cname?cap_first}("${column.x_cname}");
</#if>
</#list>
        return ${fileid_uncap_first};
    }

</#if>
<#if (X_FILEPARA1?index_of("L")>-1)>
    @Override
    public List<F${fileid_cap_first}> list${fileid_cap_first}(ShopVo shopVo, F${fileid_cap_first} ${fileid_uncap_first}) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("${fileid_cap_first}:" + this.gson.toJson(${fileid_uncap_first}));

        List<F${fileid_cap_first}> list = new ArrayList<F${fileid_cap_first}>();
        ${fileid_uncap_first}.setCardId("1");
<#list list as column>
<#if column.x_ctype=='String' && column.x_showInForm=='Y'&& column.x_ckey!='PRI'>
        ${fileid_uncap_first}.set${column.x_cname?cap_first}("${column.x_cname}");
</#if>
</#list>

        list.add(${fileid_uncap_first});
        return null;
    }

</#if>
<#if (X_FILEPARA1?index_of("P")>-1)>
    @Override
    public PageModel<F${fileid_cap_first}> page${fileid_cap_first}(ShopVo shopVo, F${fileid_cap_first} ${fileid_uncap_first}, PageCondition condition) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("${fileid_cap_first}:" + this.gson.toJson(${fileid_uncap_first}));
        System.out.println("condition:" + this.gson.toJson(condition));

        PageModel<F${fileid_cap_first}> pm = new PageModel<F${fileid_cap_first}>();
        pm.setPageIndex(condition.getPageIndex());
        pm.setPageSize(condition.getPageSize());

        ${fileid_uncap_first}.setCardId("1");
<#list list as column>
<#if column.x_ctype=='String' && column.x_showInForm=='Y'&& column.x_ckey!='PRI'>
        ${fileid_uncap_first}.set${column.x_cname?cap_first}("${column.x_cname}");
</#if>
</#list>
        pm.getResult().add(${fileid_uncap_first});
        pm.setCount(condition.getPageSize() + 1);
        return pm;
    }
 
</#if>
}