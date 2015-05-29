package net.sebinson.sample.message.collection.core.service.impl.business;

import net.sebinson.sample.message.collection.core.service.business.DataStoreService;
import net.sebinson.sample.message.collection.exceptions.CollectionDataStoreException;

public abstract class AbstractDataStoreService implements DataStoreService {

    @Override
    public int saveXXX(Object vo) throws CollectionDataStoreException {
        
        throw new CollectionDataStoreException("code", "message");
        
    }

}
