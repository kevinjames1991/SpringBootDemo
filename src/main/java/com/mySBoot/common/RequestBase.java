package com.mySBoot.common;

import java.io.Serializable;

public class RequestBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private String traceId; //请求ID
    private int lang = 1;
    private String uid; //手机ID
    private String sid; //登录会话ID
    private int platform;// 平台ID
    
    

    public String getTraceId() {
        return traceId;
    }

    public int getLang() {
        return lang;
    }

    public int getPlatform() {
        return platform;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getUid() {
        return uid;
    }

    public String getSid() {
        return sid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String log() {
        StringBuilder sb = new StringBuilder("[");
        if (traceId != null) {
            sb.append("traceId:");
            sb.append(traceId);
            sb.append(",");
        }
        if (uid != null) {
            sb.append("uid:");
            sb.append(uid);
            sb.append(",");
        }
        if (sid != null) {
            sb.append("sid:");
            sb.append(sid);
            sb.append(",");
        }
        sb.append("lang:");
        sb.append(lang);
        sb.append(",");
        sb.append("platform:");
        sb.append(platform);
        sb.append("]");
        return sb.toString();
    }


}
