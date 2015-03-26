package net.sebinson.common.cache.session.zookeeper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sebinson.common.cache.session.AbstractSession;

public class ZkSession extends AbstractSession {  
    private Map<String, Object> attributes=new HashMap<String, Object>();  
      
    public Object getAttribute(String name) {  
        return this.attributes.get(name);  
    }  
  
    public Object getValue(String name) {  
        return null;  
    }  
  
    public Enumeration<String> getAttributeNames() {  
        return null;  
    }  
  
    public String[] getValueNames() {  
        return null;  
    }  
  
    public void setAttribute(String name, Object value) {  
        this.attributes.put(name, value);  
        ZkSessionHelper.setAttribute(this.getId(), name, value);  
    }  
  
    public void putValue(String name, Object value) {  
  
    }  
  
    public void removeAttribute(String name) {  
        this.attributes.remove(name);  
        ZkSessionHelper.removeAttribute(this.getId(), name);  
    }  
  
    public void removeValue(String name) {  
  
    }  
  
    public void setAttributeLocal(String name, Object value){  
        this.attributes.put(name, value);  
    }      
}  
