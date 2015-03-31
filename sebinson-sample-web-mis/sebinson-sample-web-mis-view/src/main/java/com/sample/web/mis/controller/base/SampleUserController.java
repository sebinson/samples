package com.sample.web.mis.controller.base;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;
import net.sebinson.sample.web.common.beans.ResponseJson;
import net.sebinson.sample.web.mis.persistence.domain.SampleUser;
import net.sebinson.sample.web.mis.service.base.SampleUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/sampleuser")
public class SampleUserController {

    @Autowired
    SampleUserService sampleUserService;

    @RequestMapping(value = "/index")
    public void index() {
    }

    @RequestMapping(value = "/list")
    public @ResponseBody ModelMap list(SampleUser paramObj, Page page) {
        ModelMap modelMap = new ModelMap();
        List<SampleUser> list = this.sampleUserService.queryListPage(paramObj, page);
        modelMap.put("total", page.getRowCount());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public @ResponseBody ResponseJson add(SampleUser paramObj) {
        boolean iSuccess = false;
        if (this.sampleUserService.insert(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/remove")
    public @ResponseBody ResponseJson remove(String[] ids) {

        boolean iSuccess = false;
        if (this.sampleUserService.delete(ids) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/save")
    public @ResponseBody ResponseJson save(SampleUser paramObj) {
        boolean iSuccess = false;
        if (this.sampleUserService.update(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/byId")
    public @ResponseBody ResponseJson byId(String id) {

        boolean iSuccess = false;
        SampleUser data = this.sampleUserService.selectByKey(id);
        if (data != null) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess, data);
    }
}
