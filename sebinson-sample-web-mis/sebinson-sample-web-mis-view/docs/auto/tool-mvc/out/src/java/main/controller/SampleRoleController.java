package com.gooagoo.sample.web.controller.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.common.web.bean.ResponseJson;
import com.gooagoo.sample.persistence.domain.SampleRole;
import com.gooagoo.sample.service.base.SampleRoleService;

@Controller
@RequestMapping(value = "/samplerole")
public class SampleRoleController {

    @Autowired
    SampleRoleService sampleRoleService;

    @RequestMapping(value = "/index")
    public void index() {
    }
    
    @RequestMapping(value = "/list")
    public @ResponseBody ModelMap list(SampleRole paramObj) {
        ModelMap modelMap = new ModelMap();
        List<SampleRole> list = this.sampleRoleService.queryList(paramObj);
        modelMap.put("rows", list);
        return modelMap;
    }
    
    @RequestMapping(value = "/page")
    public @ResponseBody ModelMap page(SampleRole paramObj, Page page) {
        ModelMap modelMap = new ModelMap();
        List<SampleRole> list = this.sampleRoleService.queryListPage(paramObj, page);
        modelMap.put("total", page.getRowCount());
        modelMap.put("rows", list);
        return modelMap;
    }
    
    @RequestMapping(value = "/insert")
    public @ResponseBody ResponseJson insert(SampleRole paramObj) {
        boolean iSuccess = false;
        if (this.sampleRoleService.insert(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    
    @RequestMapping(value = "/delete")
    public @ResponseBody ResponseJson delete(String[] ids) {

        boolean iSuccess = false;
        if (this.sampleRoleService.delete(ids) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    
    @RequestMapping(value = "/update")
    public @ResponseBody ResponseJson update(SampleRole paramObj) {
        boolean iSuccess = false;
        if (this.sampleRoleService.update(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/formI")
    public String formI(SampleRole paramObj) {        
        return "samplerole/form"
    }   
    
    @RequestMapping(value = "/formU")
    public String formU(String id, ModelMap modelMap) {
        modelMap.put("data", this.sampleRoleService.selectByKey(id));
        return "samplerole/form"
    }   
               
}
