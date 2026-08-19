# JuiceRemapper

**English** | [简体中文](README_ZH.md)

JuiceRemapper is a Minecraft 1.8.9 class remapper built on [ASM](https://asm.ow2.io/), providing bidirectional mapping between obfuscated (SRG) names and MCP names. It is the underlying remapping component of the [JuiceAgent](https://github.com/xiaozhou233) ecosystem.

## Features

- **Real-time remapping**: paired with [JuiceRemapper-Native](https://github.com/xiaozhou233/JuiceRemapper-Native) to provide JVMTI-based real-time remapping
- **Bidirectional remapping**: supports both `OBF_TO_MCP` (obfuscated → MCP) and `MCP_TO_OBF` (MCP → obfuscated)
- **Inheritance-aware**: walks up the superclass chain when resolving method/field mappings
- **SRG mapping data**: bundles the 1.8.9 `vanilla.srg`
- **Descriptor remapping**: rewrites class names, field/method names and method descriptors
- **Java 8 compatible**: pinned to a JDK 8 toolchain

## Getting Started

### Download
Download the latest `JuiceRemapper-x.x-xxxx-all.jar` (fat jar with dependencies) or `JuiceRemapper-x.x-xxxx.jar` (without dependencies) from the [Release](https://github.com/xiaozhou233/JuiceRemapper/releases) page.

Also download the latest `libremapper.dll` from the [JuiceRemapper-Native](https://github.com/xiaozhou233/JuiceRemapper-Native/releases) page.

### Setup
Add `JuiceRemapper-x.x-xxxx-all.jar` to your classpath.

### Usage
```java
import cn.xiaozhou233.juiceremapper.JuiceRemapper;

...
// Load the native library
System.load("<path to your libremapper.dll>");

// Initialize
JuiceRemapper.init();

// Add a package prefix to remap
JuiceRemapper.addInclude("your/package/name/");
// Exclude a package prefix from remapping
JuiceRemapper.addExclude("your/package/name/abc/");

// See the API section below for more
```

### Result
If everything loads without errors, JuiceRemapper will automatically remap calls to unobfuscated names into their obfuscated equivalents at runtime.

### TODO
- [ ] Support more mapping versions (currently only 1.8.9 bundled)

## API

All native methods are declared on the `JuiceRemapper` class and implemented in `libremapper.dll` (JNI + JVMTI). Load the native library before using them.

### Initialization

| Method | Description |
|---|---|
| `boolean init()` | Java-side init: loads the 1.8.9 mappings, builds the inheritance graph and calls `initNative()` |
| `boolean initNative()` | Native init entry point, called internally by `init()` |

### Filters

Include / exclude rules control which packages get remapped. The argument is a package prefix (slash-separated, e.g. `your/package/`).

| Method | Description |
|---|---|
| `boolean addInclude(String name)` | Add a package prefix to remap |
| `boolean removeInclude(String name)` | Remove an include rule |
| `void clearIncludes()` | Clear all include rules |
| `boolean addExclude(String name)` | Exclude a package prefix from remapping |
| `boolean removeExclude(String name)` | Remove an exclude rule |
| `void clearExcludes()` | Clear all exclude rules |

## Building

```
./gradlew shadowJar
```

The fat jar is output to `build/libs/`.
