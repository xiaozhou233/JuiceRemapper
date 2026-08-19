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

    public String getObfuscatedOwner() {
        return fullObfuscatedName.substring(0, fullObfuscatedName.lastIndexOf("/"));
    }

    public String getObfuscatedName() {
        return fullObfuscatedName.substring(fullObfuscatedName.lastIndexOf("/") + 1);
    }

    public String getMcpOwner() {
        return fullMcpName.substring(0, fullMcpName.lastIndexOf("/"));
    }

    public String getMcpName() {
        return fullMcpName.substring(fullMcpName.lastIndexOf("/") + 1);
    }

    @Override
    public String toString() {
        return "MethodBean{" +
                "fullObfuscatedName='" + fullObfuscatedName + '\'' +
                ", obfuscatedDesc='" + obfuscatedDesc + '\'' +
                ", fullMcpName='" + fullMcpName + '\'' +
                ", mcpDesc='" + mcpDesc + '\'' +
                '}';
    }
}
