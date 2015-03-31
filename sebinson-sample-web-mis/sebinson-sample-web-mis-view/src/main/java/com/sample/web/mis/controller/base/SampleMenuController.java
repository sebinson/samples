package com.sample.web.mis.controller.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.web.common.bean.pagination.Page;
import com.sample.web.common.beans.ResponseJson;
import com.sample.web.mis.persistence.domain.SampleMenu;
import com.sample.web.mis.service.base.SampleMenuService;

@Controller
@RequestMapping(value = "/samplemenu")
public class SampleMenuController {

    @Autowired
    SampleMenuService sampleMenuService;

    @RequestMapping(value = "/index")
    public void index() {
    }

    @RequestMapping(value = "/list")
    public @ResponseBody ModelMap list(SampleMenu paramObj, Page page) {
        ModelMap modelMap = new ModelMap();
        List<SampleMenu> list = this.sampleMenuService.queryListPage(paramObj, page);
        modelMap.put("total", page.getRowCount());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public @ResponseBody ResponseJson add(SampleMenu paramObj) {
        boolean iSuccess = false;
        if (this.sampleMenuService.insert(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/remove")
    public @ResponseBody ResponseJson remove(String[] ids) {

        boolean iSuccess = false;
        if (this.sampleMenuService.delete(ids) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/save")
    public @ResponseBody ResponseJson save(SampleMenu paramObj) {
        boolean iSuccess = false;
        if (this.sampleMenuService.update(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/byId")
    public @ResponseBody ResponseJson byId(String id) {

        boolean iSuccess = false;
        SampleMenu data = this.sampleMenuService.selectByKey(id);
        if (data != null) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess, data);
    }
}
