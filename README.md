## AB分流示例项目

适用场景：外部公网应用希望使用携程集团统一AB平台，自主进行AB服务端分流，并将分流结果上报至携程集团统一AB平台进行效果分析。

## 我如何接入

1. 获取AB实验元数据（需申请gateway服务权限，联系yxuj@trip.com）
2. 根据元数据进行分流逻辑处理（详见下述使用方法）
3. 对分流结果进行上报（需申请gateway服务权限，联系yxuj@trip.com）

## 使用方法

**1、分流前提（创建实验）**：AB平台创建实验并获取实验号。如无权限，请联系 y.zhang32@trip.com。
国内实验：http://abtesting.ctripcorp.com/#/index 
海外实验：http://abtesting.bdai.sgp.tripws.com/#/index

**2、主要方法：**
本项目提供了基础的AB分流逻辑处理，核心方法为 `ABTestClient.getAlternative()`。

`ABTestDemo` 类中已提供 `main` 方法作为示例入口，Java应用可使用该模拟数据运行查看分流效果。

### 分流逻辑说明

分流按照以下优先级顺序进行：

1. **AB平台配置的白名单进行分流**：如果元数据中包含特定用户的分流配置，直接返回该用户的版本
   - 一般用于测试阶段，对应AB平台实验处于BI审核通过状态，验证各个版本结果
2. **Hash分流**：使用MD5 Hash算法，根据divisionID、domainCode、layerCode计算hash值，从mods中获取对应的版本
   - 常规分流方式，根据hash模值（0-99）匹配对应的版本

**注意**：
- 紧急分流（urgents）功能在当前版本中不支持
- 版本参数（versionParamMap）功能在当前版本中不支持

### 项目结构

```
src/main/java/com/ctrip/di/data/
├── abtest/
│   └── client/
│       ├── ABTestClient.java        # 核心分流类，提供getAlternative方法
│       ├── ABTestDemo.java          # 示例程序，提供main方法示例
│       ├── algorithm/
│       │   ├── ABFunctions.java     # 分流辅助函数
│       │   └── MD5HashGenerator.java # MD5 Hash生成器
│       └── config/
│           └── ABTestConstant.java    # 常量定义
├── abtestmetadataservice/
│   └── Metadata.java                # 元数据类
└── abtestservice/
    └── Alternative.java             # 分流结果类
```

## 注意事项

- 本项目仅提供分流逻辑处理，不包含元数据获取和上报功能
- 元数据需要从gateway服务获取
- 分流结果需要上报至gateway服务进行效果分析
