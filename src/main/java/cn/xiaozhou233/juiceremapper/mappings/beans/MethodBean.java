package cn.xiaozhou233.juiceremapper.mappings.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MethodBean {
    private String fullObfuscatedName;
    private String obfuscatedDesc;
    private String fullMcpName;
    private String mcpDesc;

    public MethodBean(String fullObfuscatedName, String obfuscatedDesc, String fullMcpName, String mcpDesc) {
        this.fullObfuscatedName = fullObfuscatedName;
        this.obfuscatedDesc = obfuscatedDesc;
        this.fullMcpName = fullMcpName;
        this.mcpDesc = mcpDesc;
    }
}
