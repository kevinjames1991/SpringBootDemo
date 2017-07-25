package com.mySBoot.common.vo;


import java.util.HashMap;
import java.util.Map;

public class JsonResult {

	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 数据
	 */
	private Object data;
	/**
	 * 附加数据
	 */
	private Map<String,Object> extData = new HashMap<String,Object>();
	/**
	 * 数据记录数
	 */
	private long total;
	
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public JsonResult setData(Object data) {
		this.data = data;
		return this;
	}
	
	public Map<String, Object> getExtData() {
		return extData;
	}

	public JsonResult putExtData(String key,Object value) {
		this.extData.put(key, value);
		return this;
	}
	public JsonResult putAllExtData(Map<String,Object> data) {
		this.extData.putAll(data);
		return this;
	}

	public long getTotal() {
		return total;
	}
	
	public JsonResult setTotal(long total) {
		this.total = total;
		return this;
	}
	
	
	private JsonResult(Object data,boolean success){
		this.success = success;
		this.data = data;
	}
	public static JsonResult success(){
		JsonResult r = new JsonResult(null,true);
		return r;
	}
	public static JsonResult failure(){
		JsonResult r = new JsonResult(null,false);
		return r;
	}

	public JsonResult() {
		
	}
}
