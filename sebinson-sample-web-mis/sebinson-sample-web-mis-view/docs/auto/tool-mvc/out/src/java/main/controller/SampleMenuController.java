package com.gooagoo.sample.web.controller.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.common.web.bean.ResponseJson;
import com.gooagoo.sample.persistence.domain.SampleMenu;
import com.gooagoo.sample.service.base.SampleMenuService;

@Controller
@RequestMapping(value = "/samplemenu")
public class SampleMenuController {

    @Autowired
    SampleMenuService sampleMenuService;

    @RequestMapping(value = "/index")
    public void index() {
    }
    
    @RequestMapping(value = "/list")
    public @ResponseBody ModelMap list(SampleMenu paramObj) {
        ModelMap modelMap = new ModelMap();
        List<SampleMenu> list = this.sampleMenuService.queryList(paramObj);
        modelMap.put("rows", list);
        return modelMap;
    }
    
    @RequestMapping(value = "/page")
    public @ResponseBody ModelMap page(SampleMenu paramObj, Page page) {
        ModelMap modelMap = new ModelMap();
        List<SampleMenu> list = this.sampleMenuService.queryListPage(paramObj, page);
        modelMap.put("total", page.getRowCount());
        modelMap.put("rows", list);
        return modelMap;
    }
    
    @RequestMapping(value = "/insert")
    public @ResponseBody ResponseJson insert(SampleMenu paramObj) {
        boolean iSuccess = false;
        if (this.sampleMenuService.insert(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    
    @RequestMapping(value = "/delete")
    public @ResponseBody ResponseJson delete(String[] ids) {

        boolean iSuccess = false;
        if (this.sampleMenuService.delete(ids) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    
    @RequestMapping(value = "/update")
    public @ResponseBody ResponseJson update(SampleMenu paramObj) {
        boolean iSuccess = false;
        if (this.sampleMenuService.update(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/formI")
    public String formI(SampleMenu paramObj) {        
        return "samplemenu/form"
    }   
    
    @RequestMapping(value = "/formU")
    public String formU(String id, ModelMap modelMap) {
        modelMap.put("map", this.sampleMenuService.selectByKey(id));
        return "samplemenu/form"
    }   
               
}
