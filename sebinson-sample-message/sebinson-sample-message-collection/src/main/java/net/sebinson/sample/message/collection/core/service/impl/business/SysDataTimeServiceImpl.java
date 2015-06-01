package net.sebinson.sample.message.collection.core.service.impl.business;

import org.springframework.stereotype.Service;

import net.sebinson.common.utils.TimeUtil;
import net.sebinson.sample.message.collection.exceptions.CollectionBusinessException;

@Service("sysDataTimeService")
public class SysDataTimeServiceImpl extends AbstractGeneralBusinessService {

    @Override
    public String getSysDateTime() throws CollectionBusinessException {

        return TimeUtil.getCurrentDateTime(TimeUtil.FORMAT6);
    }
}
