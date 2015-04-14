package com.gooagoo.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gooagoo.sample.service.SampleMenuOperationService;
import com.gooagoo.sample.vo.FSampleMenuOperation;
import com.gooagoo.shop.common.vo.PageCondition;
import com.gooagoo.shop.common.vo.PageModel;
import com.gooagoo.shop.common.vo.ResultVo;
import com.gooagoo.shop.common.vo.ShopVo;
import com.google.gson.Gson;

@Service(value = "SampleMenuOperationService")
public class SampleMenuOperationServiceImpl implements SampleMenuOperationService
{
    private final Gson gson = new Gson();

    @Override
    public ResultVo update(ShopVo shopVo, FSampleMenuOperation sampleMenuOperation) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("SampleMenuOperation:" + this.gson.toJson(sampleMenuOperation));
        return new ResultVo(false, "还在维护中...");
    }

    @Override
    public ResultVo delete(ShopVo shopVo, String operId)
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("operId:" + operId);
        return new ResultVo(false, "还在维护中...");
    }

    @Override
    public FSampleMenuOperation getSampleMenuOperation(ShopVo shopVo, String operId) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("operId:" + operId);
        FSampleMenuOperation sampleMenuOperation = new FSampleMenuOperation();
        sampleMenuOperation.setCardId(operId);
        sampleMenuOperation.setMenuId("menuId");
        sampleMenuOperation.setOperCode("operCode");
        sampleMenuOperation.setOperName("operName");
        sampleMenuOperation.setOperType("operType");
        sampleMenuOperation.setOperActions("operActions");
        return sampleMenuOperation;
    }

    @Override
    public List<FSampleMenuOperation> listSampleMenuOperation(ShopVo shopVo, FSampleMenuOperation sampleMenuOperation) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("SampleMenuOperation:" + this.gson.toJson(sampleMenuOperation));

        List<FSampleMenuOperation> list = new ArrayList<FSampleMenuOperation>();
        sampleMenuOperation.setCardId("1");
        sampleMenuOperation.setMenuId("menuId");
        sampleMenuOperation.setOperCode("operCode");
        sampleMenuOperation.setOperName("operName");
        sampleMenuOperation.setOperType("operType");
        sampleMenuOperation.setOperActions("operActions");

        list.add(sampleMenuOperation);
        return null;
    }

    @Override
    public PageModel<FSampleMenuOperation> pageSampleMenuOperation(ShopVo shopVo, FSampleMenuOperation sampleMenuOperation, PageCondition condition) throws Exception
    {
        // TODO Auto-generated method stub
        System.out.println("shopVo:" + this.gson.toJson(shopVo));
        System.out.println("SampleMenuOperation:" + this.gson.toJson(sampleMenuOperation));
        System.out.println("condition:" + this.gson.toJson(condition));

        PageModel<FSampleMenuOperation> pm = new PageModel<FSampleMenuOperation>();
        pm.setPageIndex(condition.getPageIndex());
        pm.setPageSize(condition.getPageSize());

        sampleMenuOperation.setCardId("1");
        sampleMenuOperation.setMenuId("menuId");
        sampleMenuOperation.setOperCode("operCode");
        sampleMenuOperation.setOperName("operName");
        sampleMenuOperation.setOperType("operType");
        sampleMenuOperation.setOperActions("operActions");
        pm.getResult().add(sampleMenuOperation);
        pm.setCount(condition.getPageSize() + 1);
        return pm;
    }
 
}