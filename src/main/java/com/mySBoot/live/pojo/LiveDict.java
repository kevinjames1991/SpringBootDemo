package com.mySBoot.live.pojo;

import java.io.Serializable;

/**
* tableName:LIVE_DICT
* 
* created on 2017-7-24 14:43:17
*/
public class LiveDict implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id; //主键ID
    private Double type; //类型
    private String name; //名称
    private String value; //值
    private String code; //编码
    private Long parentid; //父节点(root节点为0)
    private Integer sort; //序号
    private Integer status; //1,可用，2.取消

    public LiveDict(){
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Double getType(){
        return this.type;
    }

    public void setType(Double type){
        this.type = type;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getValue(){
        return this.value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getCode(){
        return this.code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public Long getParentid(){
        return this.parentid;
    }

    public void setParentid(Long parentid){
        this.parentid = parentid;
    }

    public Integer getSort(){
        return this.sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }

    public Integer getStatus(){
        return this.status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

}