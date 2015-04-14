package com.gooagoo.sample.service;

import java.util.List;

import com.gooagoo.shop.common.vo.PageCondition;
import com.gooagoo.shop.common.vo.PageModel;
import com.gooagoo.shop.common.vo.ResultVo;
import com.gooagoo.shop.common.vo.ShopVo;
import com.gooagoo.sample.vo.FSampleMenuOperation;

public interface SampleMenuOperationService
{
    /**
     * 修改
     * @param shopVo
     * @param sampleMenuOperation
     * @return
     * @throws Exception
     */
    public ResultVo update(ShopVo shopVo, FSampleMenuOperation sampleMenuOperation) throws Exception;

    /**
     * 删除
     * @param shopVo
     * @param operId
     * @return
     */
    public ResultVo delete(ShopVo shopVo, String operId) throws Exception;

    /**
     * 获取详情
     * @param shopVo
     * @param operId
     * @return
     * @throws Exception
     */
    public FSampleMenuOperation getSampleMenuOperation(ShopVo shopVo, String operId) throws Exception;

    /**
     * 获取列表
     * @param shopVo
     * @param sampleMenuOperation
     * @return
     * @throws Exception
     */
    public List<FSampleMenuOperation> listSampleMenuOperation(ShopVo shopVo, FSampleMenuOperation sampleMenuOperation) throws Exception;

    /**
     * 分页获取列表
     * @param shopVo
     * @param sampleMenuOperation
     * @return
     * @throws Exception
     */
    public PageModel<FSampleMenuOperation> pageSampleMenuOperation(ShopVo shopVo, FSampleMenuOperation sampleMenuOperation, PageCondition condition) throws Exception;

}
