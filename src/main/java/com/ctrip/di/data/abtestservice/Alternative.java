package com.ctrip.di.data.abtestservice;

import java.util.Map;

/**
 * AB分流结果类
 * 包含分流后的实验版本信息和相关属性
 */
public class Alternative {

    /**
     * 实验号，如 "251124_bucode_xxx"
     */
    public String expCode;

    /**
     * 分流生效时间，格式如 "2025-09-02 18:44:44"
     */
    public String effectTime;

    /**
     * 分流ID，用于标识分流的用户或实体，如 "ssss"
     */
    public String divisionID;

    /**
     * 分流版本，如 "A"、"B" 等
     */
    public String version;

    /**
     * 模值，Hash分流计算得到的模值，范围0-99，如 58。-1为白名单用户
     */
    public Integer mod;

    /**
     * 域代码，用于分层的域标识，如 "D"
     */
    public String domain;

    /**
     * 层代码，用于分层的层标识，如 "L135654"
     */
    public String layer;

    /**
     * 版本参数，包含版本相关的扩展属性
     */
    public Map<String, String> attrs;

    public String getExpCode() {
        return expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
    }

    public String getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(String effectTime) {
        this.effectTime = effectTime;
    }

    public String getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(String divisionID) {
        this.divisionID = divisionID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getMod() {
        return mod;
    }

    public void setMod(Integer mod) {
        this.mod = mod;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }

    public Alternative() {
        super();
    }

    public Alternative(String expCode, String effectTime, String divisionID, String version,
                       Integer mod, String domain, String layer, Map<String, String> attrs) {
        super();
        this.expCode = expCode;
        this.effectTime = effectTime;
        this.divisionID = divisionID;
        this.version = version;
        this.mod = mod;
        this.domain = domain;
        this.layer = layer;
        this.attrs = attrs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attrs == null) ? 0 : attrs.hashCode());
        result = prime * result + ((divisionID == null) ? 0 : divisionID.hashCode());
        result = prime * result + ((domain == null) ? 0 : domain.hashCode());
        result = prime * result + ((effectTime == null) ? 0 : effectTime.hashCode());
        result = prime * result + ((expCode == null) ? 0 : expCode.hashCode());
        result = prime * result + ((layer == null) ? 0 : layer.hashCode());
        result = prime * result + ((mod == null) ? 0 : mod.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alternative other = (Alternative) obj;
        if (attrs == null) {
            if (other.attrs != null)
                return false;
        } else if (!attrs.equals(other.attrs))
            return false;
        if (divisionID == null) {
            if (other.divisionID != null)
                return false;
        } else if (!divisionID.equals(other.divisionID))
            return false;
        if (domain == null) {
            if (other.domain != null)
                return false;
        } else if (!domain.equals(other.domain))
            return false;
        if (effectTime == null) {
            if (other.effectTime != null)
                return false;
        } else if (!effectTime.equals(other.effectTime))
            return false;
        if (expCode == null) {
            if (other.expCode != null)
                return false;
        } else if (!expCode.equals(other.expCode))
            return false;
        if (layer == null) {
            if (other.layer != null)
                return false;
        } else if (!layer.equals(other.layer))
            return false;
        if (mod == null) {
            if (other.mod != null)
                return false;
        } else if (!mod.equals(other.mod))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Alternative [expCode=" + expCode + ", effectTime=" + effectTime + ", divisionID="
                + divisionID + ", version=" + version + ", mod=" + mod + ", domain=" + domain + ", layer="
                + layer + ", attrs=" + attrs + "]";
    }


}
