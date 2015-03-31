package com.sample.web.mis.controller.system;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;
import net.sebinson.sample.web.common.beans.ResponseJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.web.mis.persistence.domain.SampleMenu;
import com.sample.web.mis.service.base.SampleMenuService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    SampleMenuService menuService;

    @RequestMapping(value = "/index")
    public void index(ModelMap modelMap) {
    }

    @RequestMapping(value = "/list")
    public @ResponseBody ModelMap list(SampleMenu paramObj, Page page) {
        ModelMap modelMap = new ModelMap();
        List<SampleMenu> list = this.menuService.queryListPage(paramObj, page);
        modelMap.put("total", page.getRowCount());
        modelMap.put("rows", list);
        return modelMap;
    }

    @RequestMapping(value = "/add")
    public @ResponseBody ResponseJson add(SampleMenu paramObj) {
        boolean iSuccess = false;
        if (menuService.insert(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/remove")
    public @ResponseBody ResponseJson remove(String[] ids) {

        boolean iSuccess = false;
        if (menuService.delete(ids) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/save")
    public @ResponseBody ResponseJson save(SampleMenu paramObj) {
        boolean iSuccess = false;
        if (menuService.update(paramObj) > 0) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess);
    }

    @RequestMapping(value = "/byId")
    public @ResponseBody ResponseJson byId(String id) {

        boolean iSuccess = false;
        SampleMenu data = menuService.selectByKey(id);
        if (data != null) {
            iSuccess = true;
        }
        return ResponseJson.body(iSuccess, data);
    }
}
