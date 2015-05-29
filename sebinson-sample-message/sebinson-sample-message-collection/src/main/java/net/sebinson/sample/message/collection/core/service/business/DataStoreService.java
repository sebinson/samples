package net.sebinson.sample.message.collection.core.service.business;

import net.sebinson.sample.message.collection.exceptions.CollectionDataStoreException;

/**
 * 
 * 
 * @author C
 */
public interface DataStoreService {

    /**
     * 
     * @param entity
     * @return
     * @throws CollectionDataStoreException 
     */
    public int saveXXX(Object vo) throws CollectionDataStoreException;
    
}
