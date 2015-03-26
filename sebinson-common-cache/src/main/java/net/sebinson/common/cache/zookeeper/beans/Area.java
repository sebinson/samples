package net.sebinson.common.cache.zookeeper.beans;

import java.io.Serializable;

public class Area implements Serializable {

    private static final long serialVersionUID = 8819129236054929507L;
    private String id;// 省市区编码
    private String name;// 省市区名称
    private String parentId;// 上级编码
    private String parentName;// 上级名称
    private Integer sortOrder;// 排序

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}
