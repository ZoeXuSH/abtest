package com.ctrip.di.data.abtest.client;

import com.ctrip.di.data.abtestmetadataservice.Metadata;
import com.ctrip.di.data.abtestservice.Alternative;

import java.util.HashMap;
import java.util.Map;

/**
 * AB分流示例程序
 * 提供main方法作为示例入口
 */
public final class ABTestDemo {

    public static void main(String[] args) {
        // 实验号，需先从AB平台申请
        String expCode = "240830_PLC_xxx";
        // 分流ID，如用户ID（可以使用白名单中的用户ID测试白名单分流，或使用其他ID测试Hash分流）
        String divisionID = "ssss";

        // 创建模拟实验元数据（实际使用时需从gateway服务获取）
        Metadata metadata = createMockMetadata(expCode);

        // 进行分流
        Alternative alternative = ABTestClient.getAlternative(expCode, divisionID, metadata);

        // 输出分流结果
        System.out.println("分流结果：" + alternative.toString());

        // 根据分流结果执行相应的业务逻辑
        String version = alternative.getVersion();
        switch (version) {
            case "A":
                // 执行版本A的逻辑
                System.out.println("执行版本A的逻辑");
                break;
            case "B":
                // 执行版本B的逻辑
                System.out.println("执行版本B的逻辑");
                break;
            case "C":
                // 执行版本C的逻辑
                System.out.println("执行版本C的逻辑");
                break;
            case "D":
                // 执行版本D的逻辑
                System.out.println("执行版本D的逻辑");
                break;
            default:
                // 处理未命中的情况,业务方需要兜底逻辑，一般保持和A版本(原版)一致
                System.out.println("未命中任何版本，执行默认逻辑");
                break;
        }
    }

    /**
     * 创建模拟实验元数据
     * 实际使用时需从gateway服务获取真实的实验元数据
     *
     * @param expCode 实验号
     * @return 模拟的元数据对象
     */
    private static Metadata createMockMetadata(String expCode) {
        Metadata metadata = new Metadata();
        metadata.setCode(expCode);
        metadata.setEffectTime("2025-09-02 18:44:44");
        metadata.setDomainCode("D");
        metadata.setLayerCode("L135654");

        // 设置白名单分流配置（specifics）：divisionID -> 版本号
        Map<String, String> specifics = createMockSpecifics();
        metadata.setSpecifics(specifics);

        // 设置Hash分流配置（mods）：模值 -> 版本号
        Map<String, String> mods = createMockMods();
        metadata.setMods(mods);

        return metadata;
    }

    /**
     * 创建模拟的白名单分流配置
     *
     * @return 白名单配置映射
     */
    private static Map<String, String> createMockSpecifics() {
        Map<String, String> specifics = new HashMap<>();

        // 版本A的白名单用户
        specifics.put("123444", "A");

        // 版本B的白名单用户
        specifics.put("xxxxyyyy", "B");

        return specifics;
    }

    /**
     * 创建模拟的实验Hash分流配置
     * 版本A：模值 4-49, 52-74, 77-99
     * 版本B：模值 0-3
     * 版本C：模值 50-51
     * 版本D：模值 75-76
     *
     * @return Hash分流配置映射
     */
    private static Map<String, String> createMockMods() {
        Map<String, String> mods = new HashMap<>();

        // 版本B：0-3
        addModRange(mods, 0, 3, "B");
        // 版本A：4-49
        addModRange(mods, 4, 49, "A");
        // 版本C：50-51
        addModRange(mods, 50, 51, "C");
        // 版本A：52-74
        addModRange(mods, 52, 74, "A");
        // 版本D：75-76
        addModRange(mods, 75, 76, "D");
        // 版本A：77-99
        addModRange(mods, 77, 99, "A");

        return mods;
    }

    /**
     * 辅助方法：将模值范围添加到mods映射中
     *
     * @param mods    模值到版本的映射
     * @param start   起始模值（包含）
     * @param end     结束模值（包含）
     * @param version 版本号
     */
    private static void addModRange(Map<String, String> mods, int start, int end, String version) {
        for (int i = start; i <= end; i++) {
            mods.put(String.valueOf(i), version);
        }
    }
}
