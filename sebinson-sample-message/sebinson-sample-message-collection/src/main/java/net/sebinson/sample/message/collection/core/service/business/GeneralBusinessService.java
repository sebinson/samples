package net.sebinson.sample.message.collection.core.service.business;

import net.sebinson.sample.message.collection.exceptions.CollectionBusinessException;
import net.sebinson.sample.message.collection.exceptions.CollectionException;

/**
 * 
 * 
 * @author C
 */
public interface GeneralBusinessService {

    /**
     * 
     * @param adds
     * @return
     * @throws CollectionException
     */
    public Object login(String cid) throws CollectionBusinessException;

    /**
     * 
     * @return
     */
    public String getSysDateTime() throws CollectionBusinessException;
}
