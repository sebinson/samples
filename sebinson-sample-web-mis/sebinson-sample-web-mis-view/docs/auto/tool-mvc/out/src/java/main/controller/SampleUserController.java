package com.gooagoo.sample.web.controller.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.common.web.bean.ResponseJson;
import com.gooagoo.sample.persistence.domain.SampleUser;
import com.gooagoo.sample.service.base.SampleUserService;

@Controller
@RequestMapping(value = "/sampleuser")
public class SampleUserController {

    @Autowired
    SampleUserService sampleUserService;

    @RequestMapping(value = "/index")
    public void index() {
    }
    
    @RequestMapping(value = "/list")
    public @ResponseBody ModelMap list(SampleUser paramObj) {
        ModelMap modelMap = new ModelMap();
        List<SampleUser> list = this.sampleUserService.queryList(paramObj);
        modelMap.put("rows", list);
        return modelMap;
    }
    
    @RequestMapping(value = "/page")
    public @ResponseBody ModelMap page(SampleUser paramObj, Page page) {
        ModelMap modelMap = new ModelMap();
        List<SampleUser> list = this.sampleUserService.queryListPage(paramObj, page);
        modelMap.put("total", page.getRowCount());
        modelMap.put("rows", list);
        return modelMap;
    }
    
    @RequestMapping(value = "/insert")
    public @ResponseBody ResponseJson insert(SampleUser paramObj) {
        boolean iSuccess = false;
        if (this.sampleUserService.insert(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    
    @RequestMapping(value = "/delete")
    public @ResponseBody ResponseJson delete(String[] ids) {

        boolean iSuccess = false;
        if (this.sampleUserService.delete(ids) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    
    @RequestMapping(value = "/update")
    public @ResponseBody ResponseJson update(SampleUser paramObj) {
        boolean iSuccess = false;
        if (this.sampleUserService.update(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/formI")
    public String formI(SampleUser paramObj) {        
        return "sampleuser/form"
    }   
    
    @RequestMapping(value = "/formU")
    public String formU(String id, ModelMap modelMap) {
        modelMap.put("data", this.sampleUserService.selectByKey(id));
        return "sampleuser/form"
    }   
               
}
