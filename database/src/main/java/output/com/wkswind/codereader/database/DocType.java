package com.wkswind.codereader.database;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "DOC_TYPE".
 */
public class DocType {

    private String name;
    private String extensions;
    private Long id;
    private Integer status;
    private String remark;

    public DocType() {
    }

    public DocType(Long id) {
        this.id = id;
    }

    public DocType(String name, String extensions, Long id, Integer status, String remark) {
        this.name = name;
        this.extensions = extensions;
        this.id = id;
        this.status = status;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
