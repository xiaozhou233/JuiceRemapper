package cn.xiaozhou233.juiceremapper.mappings.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldBean {
    private String obfuscatedOwner;
    private String obfuscatedName;
    private String mcpOwner;
    private String mcpName;

    public FieldBean(String obfuscatedOwner, String obfuscatedName, String mcpOwner, String mcpName) {
        this.obfuscatedOwner = obfuscatedOwner;
        this.obfuscatedName = obfuscatedName;
        this.mcpOwner = mcpOwner;
        this.mcpName = mcpName;
    }

    public String getObfuscatedFullName() {
        return obfuscatedOwner + "/" + obfuscatedName;
    }

    public String getMcpFullName() {
        return mcpOwner + "/" + mcpName;
    }

    @Override
    public String toString() {
        return "FieldBean{" +
                "obfuscatedOwner='" + obfuscatedOwner + '\'' +
                ", obfuscatedName='" + obfuscatedName + '\'' +
                ", mcpOwner='" + mcpOwner + '\'' +
                ", mcpName='" + mcpName + '\'' +
                '}';
    }
}
