package net.sebinson.sample.message.collection.core.service.impl.business;

import org.springframework.stereotype.Service;

import net.sebinson.sample.message.collection.exceptions.CollectionBusinessException;

@Service("loginBusinessService")
public class LoginBusinessServiceImpl extends AbstractGeneralBusinessService {

    @Override
    public Object login(String cid) throws CollectionBusinessException {

        //TODO 具体的登入业务
        return null;
    }
}
