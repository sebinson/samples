package net.sebinson.sample.message.collection.core.service.impl.business;

import net.sebinson.sample.message.collection.core.service.business.GeneralBusinessService;
import net.sebinson.sample.message.collection.exceptions.CollectionBusinessException;

public abstract class AbstractGeneralBusinessService implements GeneralBusinessService {

    @Override
    public Object login(String cid) throws CollectionBusinessException {

        throw new CollectionBusinessException("code", "message");
    }

    @Override
    public String getSysDateTime() throws CollectionBusinessException {

        throw new CollectionBusinessException("code", "message");
    }

}
