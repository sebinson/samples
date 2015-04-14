package com.gooagoo.sample.web.controller.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.common.web.bean.ResponseJson;
import com.gooagoo.sample.persistence.domain.SampleMenuOperation;
import com.gooagoo.sample.service.base.SampleMenuOperationService;

@Controller
@RequestMapping(value = "/samplemenuoperation")
public class SampleMenuOperationController {

    @Autowired
    SampleMenuOperationService sampleMenuOperationService;

    @RequestMapping(value = "/index")
    public void index() {
    }
    
    @RequestMapping(value = "/list")
    public @ResponseBody ModelMap list(SampleMenuOperation paramObj) {
        ModelMap modelMap = new ModelMap();
        List<SampleMenuOperation> list = this.sampleMenuOperationService.queryList(paramObj);
        modelMap.put("rows", list);
        return modelMap;
    }
    
    @RequestMapping(value = "/page")
    public @ResponseBody ModelMap page(SampleMenuOperation paramObj, Page page) {
        ModelMap modelMap = new ModelMap();
        List<SampleMenuOperation> list = this.sampleMenuOperationService.queryListPage(paramObj, page);
        modelMap.put("total", page.getRowCount());
        modelMap.put("rows", list);
        return modelMap;
    }
    
    @RequestMapping(value = "/insert")
    public @ResponseBody ResponseJson insert(SampleMenuOperation paramObj) {
        boolean iSuccess = false;
        if (this.sampleMenuOperationService.insert(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    
    @RequestMapping(value = "/delete")
    public @ResponseBody ResponseJson delete(String[] ids) {

        boolean iSuccess = false;
        if (this.sampleMenuOperationService.delete(ids) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }
    
    @RequestMapping(value = "/update")
    public @ResponseBody ResponseJson update(SampleMenuOperation paramObj) {
        boolean iSuccess = false;
        if (this.sampleMenuOperationService.update(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/formI")
    public String formI(SampleMenuOperation paramObj) {        
        return "samplemenuoperation/form"
    }   
    
    @RequestMapping(value = "/formU")
    public String formU(String id, ModelMap modelMap) {
        modelMap.put("data", this.sampleMenuOperationService.selectByKey(id));
        return "samplemenuoperation/form"
    }   
               
}
