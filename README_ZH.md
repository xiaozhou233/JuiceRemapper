# JuiceRemapper
# 这个项目目前不再更新，因为目前版本的OrangeX客户端已经打算不再支持Badlion/Vanilla 
已知的BUG有：不能识别部分继承关系，例如mc.theWorld.loadedEntityList会映射成ave.xxx.loadedEntityList而不是ave.xxx.yyy，在混淆环境(Badlion/Vanilla)不会调用正确的字段而报错

[English](README.md) | **简体中文**

JuiceRemapper 是基于 [ASM](https://asm.ow2.io/) 的 Minecraft 1.8.9 类重映射器，用于在混淆名（SRG/Obfuscated）与 MCP 名之间双向转换，是 [JuiceAgent](https://github.com/xiaozhou233) 生态的底层重映射组件。

## 功能特性

- **实时映射**：配合 [JuiceRemapper-Native](https://github.com/xiaozhou233/JuiceRemapper-Native) 提供 JVMTI 实时重映射能力
- **双向重映射**：支持 `OBF_TO_MCP`（混淆 → MCP）与 `MCP_TO_OBF`（MCP → 混淆）两种方向
- **继承感知**：查找方法/字段映射时自动沿父类链向上回溯，正确处理继承成员
- **SRG 映射文件**：内置 1.8.9 的 `vanilla.srg`
- **描述符重映射**：类名、字段名、方法名及签名描述符全部重写
- **Java 8 兼容**：工具链固定为 JDK 8

## 快速开始

### 下载
从 [Release](https://github.com/xiaozhou233/JuiceRemapper/releases) 页面下载最新版本：`JuiceRemapper-x.x-xxxx-all.jar`（fat jar，包含依赖）或 `JuiceRemapper-x.x-xxxx.jar`（不含依赖）。

从 [JuiceRemapper-Native](https://github.com/xiaozhou233/JuiceRemapper-Native/releases) 页面下载最新版本的 `libremapper.dll`。

### 配置
将 `JuiceRemapper-x.x-xxxx-all.jar` 添加到你的 classpath（类路径）。

### 使用
```java
import cn.xiaozhou233.juiceremapper.JuiceRemapper;

...
// 加载 native 库
System.load("<你的 libremapper.dll 路径>");

// 初始化
JuiceRemapper.init();

// 添加需要重映射的包（参数为包名前缀）
JuiceRemapper.addInclude("your/package/name/");
// 排除不需要重映射的包
JuiceRemapper.addExclude("your/package/name/abc/");

// 更多 API 见下文「API」部分
```

### 使用效果
若加载无误，JuiceRemapper 会自动将未混淆的代码调用重映射为对应的混淆代码调用，实现运行时改名。

### TODO
- [ ] 添加更多映射版本支持（当前仅内置 1.8.9）

## API

所有 native 方法均由 `JuiceRemapper` 类声明，实现在 `libremapper.dll`（JNI + JVMTI）中，使用前需先加载原生库。

### 初始化

| 方法 | 说明 |
|---|---|
| `boolean init()` | Java 侧初始化：加载 1.8.9 映射、建立继承图，并调用 `initNative()` |
| `boolean initNative()` | native 初始化入口，仅由 `init()` 内部调用 |

### 过滤规则

通过 include / exclude 控制需要重映射的包范围，参数为包名前缀（斜杠分隔，如 `your/package/`）。

| 方法 | 说明 |
|---|---|
| `boolean addInclude(String name)` | 添加需要重映射的包前缀 |
| `boolean removeInclude(String name)` | 移除 include 规则 |
| `void clearIncludes()` | 清空所有 include 规则 |
| `boolean addExclude(String name)` | 添加排除的包前缀 |
| `boolean removeExclude(String name)` | 移除 exclude 规则 |
| `void clearExcludes()` | 清空所有 exclude 规则 |

## 如何构建

```
./gradlew shadowJar
```

构建产物 fat jar 默认输出到 `build/libs/`。
