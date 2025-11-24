package com.ctrip.di.data.abtest.client;

import com.ctrip.di.data.abtest.client.algorithm.MD5HashGenerator;
import com.ctrip.di.data.abtest.client.config.ABTestConstant;
import com.ctrip.di.data.abtestmetadataservice.Metadata;
import com.ctrip.di.data.abtestservice.Alternative;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * AB分流客户端
 * 提供基础的分流逻辑处理
 */
public final class ABTestClient {

    /**
     * 根据元数据进行分流
     *
     * @param expCode    实验号，http://abtesting.ctripcorp.com/#/index创建实验后获取，格式如 200330_IFLT_test（请保持和平台一致，不要转大小写）
     * @param divisionID 分流ID，如 cid, uid, vid 等可以唯一标识用户的 ID
     * @param metadata   实验元数据（已从gateway服务获取到的实验元数据）
     * @return 分流结果
     *
     * @apiNote 建议结合项目进行日志监控，记录以下信息以便排障：
     *          - 调用次数：统计方法调用频率，统计量级
     *          - 分流结果分布：记录分到各个版本（A/B/C等）的用户数量
     *          - 分流详情：记录expCode、divisionID、version、mod等关键信息
     *          - 异常情况：记录分流失败或返回默认值的情况
     *          这些监控数据有助于验证分流逻辑是否正确执行、检查各版本流量分配是否符合预期、快速定位分流异常问题。
     */
    public static Alternative getAlternative(final String expCode,
                                             final String divisionID,
                                             final Metadata metadata) {
        if (StringUtils.isBlank(expCode)) {
            return new Alternative(expCode, null, divisionID, ABTestConstant.DEFAULT_VERSION,
                    ABTestConstant.DEFAULT_MOD, null, null, null);
        }
        if (StringUtils.isBlank(divisionID)) {
            return new Alternative(expCode, null, divisionID, ABTestConstant.DEFAULT_VERSION,
                    ABTestConstant.DEFAULT_MOD, null, null, null);
        }
        if (!expCode.matches(ABTestConstant.EXPCODE_REGEX)) {
            return new Alternative(expCode, null, divisionID, ABTestConstant.DEFAULT_VERSION,
                    ABTestConstant.DEFAULT_MOD, null, null, null);
        }
        if (metadata == null) {
            return new Alternative(expCode, null, divisionID, ABTestConstant.DEFAULT_VERSION,
                    ABTestConstant.DEFAULT_MOD, null, null, null);
        }

        return toAlternative(metadata, divisionID);
    }

    /**
     * 核心分流逻辑
     */
    private static Alternative toAlternative(Metadata metadata, String divisionID) {
        // 1. 根据AB平台上配置的版本白名单分流（一般用于测试阶段，对应AB平台实验处于BI审核通过状态，验证各个版本结果）
        Map<String, String> specifics = metadata.getSpecifics();
        if (specifics != null && !specifics.isEmpty()) {
            String version = specifics.get(divisionID);
            if (StringUtils.isNotBlank(version)) {
                return new Alternative(metadata.getCode(), metadata.getEffectTime(), divisionID, version,
                        ABTestConstant.DEFAULT_MOD, metadata.getDomainCode(), metadata.getLayerCode(),
                        null);
            }
        }

        // 2. 使用Hash进行常规分流（mods）
        Map<String, String> mods = metadata.getMods();
        if (mods == null || mods.isEmpty()) {
            return new Alternative(metadata.getCode(), metadata.getEffectTime(), divisionID,
                    ABTestConstant.DEFAULT_VERSION, ABTestConstant.DEFAULT_MOD,
                    metadata.getDomainCode(), metadata.getLayerCode(), null);
        }

        int mod = MD5HashGenerator.hash(divisionID, metadata.getDomainCode(), metadata.getLayerCode());
        String version = mods.get(String.valueOf(mod));
        if (version != null) {
            return new Alternative(metadata.getCode(), metadata.getEffectTime(), divisionID, version, mod,
                    metadata.getDomainCode(), metadata.getLayerCode(), null);
        }

        return new Alternative(metadata.getCode(), metadata.getEffectTime(), divisionID,
                ABTestConstant.DEFAULT_VERSION, mod, metadata.getDomainCode(), metadata.getLayerCode(), null);
    }
}

