package com.gooagoo.sample.web.controller.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.common.web.bean.ResponseJson;
import com.gooagoo.sample.persistence.domain.${fileid_cap_first};
import com.gooagoo.sample.service.base.${fileid_cap_first}Service;

@Controller
@RequestMapping(value = "/${fileid_uncap}")
public class ${fileid_cap_first}Controller {

    @Autowired
    ${fileid_cap_first}Service ${fileid_uncap_first}Service;

    <#if (X_FILEPARA1?index_of("L") > -1)||(X_FILEPARA1?index_of("P") > -1)>
    @RequestMapping(value = "/index")
    public void index() {
    }
    </#if>
    
    <#if (X_FILEPARA1?index_of("L") > -1)>
    @RequestMapping(value = "/list")
    public @ResponseBody ModelMap list(${fileid_cap_first} paramObj) {
        ModelMap modelMap = new ModelMap();
        List<${fileid_cap_first}> list = this.${fileid_uncap_first}Service.queryList(paramObj);
        modelMap.put("rows", list);
        return modelMap;
    }
    </#if>
    
    <#if (X_FILEPARA1?index_of("P")>-1)>
    @RequestMapping(value = "/page")
    public @ResponseBody ModelMap page(${fileid_cap_first} paramObj, Page page) {
        ModelMap modelMap = new ModelMap();
        List<${fileid_cap_first}> list = this.${fileid_uncap_first}Service.queryListPage(paramObj, page);
        modelMap.put("total", page.getRowCount());
        modelMap.put("rows", list);
        return modelMap;
    }
    </#if>
    
    <#if (X_FILEPARA1?index_of("I")>-1)>
    @RequestMapping(value = "/insert")
    public @ResponseBody ResponseJson insert(${fileid_cap_first} paramObj) {
        boolean iSuccess = false;
        if (this.${fileid_uncap_first}Service.insert(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    </#if>
    
    <#if (X_FILEPARA1?index_of("D")>-1)>
    @RequestMapping(value = "/delete")
    public @ResponseBody ResponseJson delete(String[] ids) {

        boolean iSuccess = false;
        if (this.${fileid_uncap_first}Service.delete(ids) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    </#if> 
    
    <#if (X_FILEPARA1?index_of("U")>-1)>
    @RequestMapping(value = "/update")
    public @ResponseBody ResponseJson update(${fileid_cap_first} paramObj) {
        boolean iSuccess = false;
        if (this.${fileid_uncap_first}Service.update(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    </#if>  

    <#if (X_FILEPARA1?index_of("!")>-1)>
        <#if (X_FILEPARA1?index_of("I")>-1)>
    @RequestMapping(value = "/formI")
    public String formI(${fileid_cap_first} paramObj) {        
        return "${fileid_uncap}/form"
    }   </#if>
    
        <#if (X_FILEPARA1?index_of("U")>-1)>
    @RequestMapping(value = "/formU")
    public String formU(String id, ModelMap modelMap) {
        modelMap.put("map", this.${fileid_uncap_first}Service.selectByKey(id));
        return "${fileid_uncap}/form"
    }   </#if>
               
    <#else>
        <#if (X_FILEPARA1?index_of("U")>-1)>        
    @RequestMapping(value = "/byId")
    public @ResponseBody ResponseJson byId(String id) {
        boolean iSuccess = false;
        ${fileid_cap_first} data = this.${fileid_uncap_first}Service.selectByKey(id);
        if (data != null) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess, data);
    }   </#if>    
    </#if>
}
