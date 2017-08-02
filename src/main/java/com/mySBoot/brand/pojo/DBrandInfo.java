package com.mySBoot.brand.pojo;

import java.io.Serializable;
import java.util.Date;

public class DBrandInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long supplyid;

    private Short lang;

    private String brandname;

    private String industry;

    private String logourl;

    private String description;

    private String format;

    private String fromto;

    private String remark;

    private Integer status;

    private Long createby;

    private Date createtime;

    private Long updateby;

    private Date updatetime;

    private Long moduleid;

    private String humantype;

    private Long hcode;

    private String html;

    private Long flowseq;

    private Long version;

    private String englishName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplyid() {
        return supplyid;
    }

    public void setSupplyid(Long supplyid) {
        this.supplyid = supplyid;
    }

    public Short getLang() {
        return lang;
    }

    public void setLang(Short lang) {
        this.lang = lang;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl == null ? null : logourl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public String getFromto() {
        return fromto;
    }

    public void setFromto(String fromto) {
        this.fromto = fromto == null ? null : fromto.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateby() {
        return createby;
    }

    public void setCreateby(Long createby) {
        this.createby = createby;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getUpdateby() {
        return updateby;
    }

    public void setUpdateby(Long updateby) {
        this.updateby = updateby;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getModuleid() {
        return moduleid;
    }

    public void setModuleid(Long moduleid) {
        this.moduleid = moduleid;
    }

    public String getHumantype() {
        return humantype;
    }

    public void setHumantype(String humantype) {
        this.humantype = humantype == null ? null : humantype.trim();
    }

    public Long getHcode() {
        return hcode;
    }

    public void setHcode(Long hcode) {
        this.hcode = hcode;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html == null ? null : html.trim();
    }

    public Long getFlowseq() {
        return flowseq;
    }

    public void setFlowseq(Long flowseq) {
        this.flowseq = flowseq;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }
}