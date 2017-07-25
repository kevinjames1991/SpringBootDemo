package com.mySBoot.common.vo;

import org.apache.commons.lang3.StringUtils;

public enum ErrorMessage {
	SYS_SUCCESS_DEFAULT("0000","成功"),

    SYS_UNKNOWN_ERROR ("0001","未知错误"),
    SYS_URL_FORMAT_ERROR ("0002","URL格式错误代码"),
    SYS_SERVICE_NOT_FOUND ("0003","找不到服务"),
    SYS_REQUEST_PACKAGE_ERROR ("0004","请求错误包代码"),
    SYS_METHOD_TYPE_ERROR ("0005","方法类型错误代码"),

    // 全局错误
    GLOBAL_PARAMETER_NULL ("0021","参数为空"),
    GLOBAL_PARAMETER_FORMAT_ERROR ("0022","参数格式化错误"),
    GLOBAL_PARAMETER_ERROR ("0023","参数错误"),
    GLOBAL_PARAMETER_EMPTY ("0024","参数是空字符"),
    GLOBAL_OBJECT_EXISTS ("0025","对象已经存在"),
    GLOBAL_OBJECT_NOT_EXISTS ("0026","对象不存在"),
    GLOBAL_PARAMETER_LENGTH_MORE_THAN ("0027","参数长度超过预定长度");
	
	private String code;
    private String message;
    
	ErrorMessage(String code,String message){
		this.code = code;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}
	
	public static String getMessageByCode(String code){
	    for(ErrorMessage error : ErrorMessage.values()){
	      if(StringUtils.equals(code, error.getCode())){
	        return error.getMessage();
	      }
	    }
	    return "未知错误";
	  }
	
}

