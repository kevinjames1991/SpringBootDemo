package com.mySBoot.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBase {
    private String ecode = ErrorCode.SYS_SUCCESS_DEFAULT;
    private String emsg = ErrorMessage.getMessageByCode(ErrorCode.SYS_SUCCESS_DEFAULT);
    private String traceId;
    private Object data;
    private String isUseH5Cart;
    private int json = JsonType.GSON.ordinal();//0gson,1jackson
    public ResponseBase(){
    	
    }
    
    public ResponseBase(RequestBase request){
    	this.traceId = request.getTraceId();
    	this.ecode = ErrorCode.SYS_SUCCESS_DEFAULT;
    	this.emsg = ErrorMessage.getMessageByCode(ErrorCode.SYS_SUCCESS_DEFAULT);
    }

    public enum JsonType{
        GSON,JACKSON
    }

    public String getIsUseH5Cart() {
        return isUseH5Cart;
    }

    public void setIsUseH5Cart(String isUseH5Cart) {
        this.isUseH5Cart = isUseH5Cart;
    }

    public String getEcode() {
        return ecode;
    }

    public String getEmsg() {
        return emsg;
    }

    public String getTraceId() {
        return traceId;
    }

    public Object getData() {
        return data;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public void setEmsg(String emsg) {
        this.emsg = emsg;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @JsonIgnore
    public int getJson() {
        return json;
    }

    public void setJson(int json) {
        this.json = json;
    }
}
