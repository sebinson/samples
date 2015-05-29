package net.sebinson.sample.message.collection.core.service.impl.business;

import net.sebinson.common.utils.TimeUtil;
import net.sebinson.sample.message.collection.exceptions.CollectionBusinessException;

public class SysDataTimeServiceImpl extends AbstractGeneralBusinessService {

    @Override
    public String getSysDateTime() throws CollectionBusinessException {

        return TimeUtil.getCurrentDateTime(TimeUtil.FORMAT6);
    }
}
