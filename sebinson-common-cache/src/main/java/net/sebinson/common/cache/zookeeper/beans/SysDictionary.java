package net.sebinson.common.cache.zookeeper.beans;

import java.io.Serializable;

public class SysDictionary implements Serializable {

    private static final long serialVersionUID = -4498688686340888957L;
    private String type;// 字典类型编码，如sex
    private String typeName;// 字典类型名称，如性别
    private String code;// 字典编号，如M
    private String codeName;// 字典中文名称，如男
    private String note;// 备注

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
