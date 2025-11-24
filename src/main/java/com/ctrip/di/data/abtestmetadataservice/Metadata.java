package com.ctrip.di.data.abtestmetadataservice;

import java.util.List;
import java.util.Map;

/**
 * AB实验元数据信息，包含分流逻辑需要的字段
 */
public class Metadata {
    /**
     * 实验号，如 "251124_bucode_xxx"
     */
    private String code;

    /**
     * 生效时间，格式如 "2025-09-02 18:44:44"
     */
    private String effectTime;

    /**
     * 域代码，用于分层的域标识，如 "D"
     */
    private String domainCode;

    /**
     * 层代码，用于分层的层标识，如 "L135654"
     */
    private String layerCode;

    /**
     * 特定用户分流配置，key为divisionID，value为版本号
     * 优先级最高，如果divisionID在此配置中，直接返回对应版本
     */
    private Map<String, String> specifics;

    /**
     * 紧急分流列表，用于特定系统、App ID、App版本条件下的紧急分流
     * 注意：此功能在当前版本中不支持
     */
    private List<Urgent> urgents;

    /**
     * Hash分流配置，key为模值（0-99的字符串），value为版本号
     * 优先级最低，根据divisionID、domainCode、layerCode计算hash模值后匹配
     */
    private Map<String, String> mods;

    /**
     * 实验属性，包含实验的扩展属性信息
     */
    private Map<String, String> attrs;

    /**
     * 版本参数映射，key为版本号（A/B/C/D等），value为版本参数的JSON字符串
     * 用于存储每个版本对应的参数配置
     * 注意：此功能在当前版本中不支持
     */
    private Map<String, String> versionParamMap;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(String effectTime) {
        this.effectTime = effectTime;
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }

    public String getLayerCode() {
        return layerCode;
    }

    public void setLayerCode(String layerCode) {
        this.layerCode = layerCode;
    }

    public Map<String, String> getSpecifics() {
        return specifics;
    }

    public void setSpecifics(Map<String, String> specifics) {
        this.specifics = specifics;
    }

    public List<Urgent> getUrgents() {
        return urgents;
    }

    public void setUrgents(List<Urgent> urgents) {
        this.urgents = urgents;
    }

    public Map<String, String> getMods() {
        return mods;
    }

    public void setMods(Map<String, String> mods) {
        this.mods = mods;
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, String> attrs) {
        this.attrs = attrs;
    }

    public Map<String, String> getVersionParamMap() {
        return versionParamMap;
    }

    public void setVersionParamMap(Map<String, String> versionParamMap) {
        this.versionParamMap = versionParamMap;
    }
}

