package com.sample.web.mis.controller.base;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;
import net.sebinson.sample.web.common.beans.ResponseJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.web.mis.persistence.domain.SampleRole;
import com.sample.web.mis.service.base.SampleRoleService;

@Controller
@RequestMapping(value = "/samplerole")
public class SampleRoleController {

    @Autowired
    SampleRoleService sampleRoleService;

    @RequestMapping(value = "/index")
    public void index() {
    }

    @RequestMapping(value = "/list")
    public @ResponseBody ModelMap list(SampleRole paramObj, Page page) {
        ModelMap modelMap = new ModelMap();
        List<SampleRole> list = this.sampleRoleService.queryListPage(paramObj, page);
        modelMap.put("total", page.getRowCount());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public @ResponseBody ResponseJson add(SampleRole paramObj) {
        boolean iSuccess = false;
        if (this.sampleRoleService.insert(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/remove")
    public @ResponseBody ResponseJson remove(String[] ids) {

        boolean iSuccess = false;
        if (this.sampleRoleService.delete(ids) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/save")
    public @ResponseBody ResponseJson save(SampleRole paramObj) {
        boolean iSuccess = false;
        if (this.sampleRoleService.update(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/byId")
    public @ResponseBody ResponseJson byId(String id) {

        boolean iSuccess = false;
        SampleRole data = this.sampleRoleService.selectByKey(id);
        if (data != null) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess, data);
    }
}
