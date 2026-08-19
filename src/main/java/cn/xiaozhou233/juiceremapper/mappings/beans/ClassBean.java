package cn.xiaozhou233.juiceremapper.mappings.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassBean {
    private String obfuscatedName;
    private String mcpName;

    public ClassBean(String obfuscatedName, String mcpName) {
        this.obfuscatedName = obfuscatedName;
        this.mcpName = mcpName;
    }

    @Override
    public String toString() {
        return "ClassBean{" +
                "obfuscatedName='" + obfuscatedName + '\'' +
                ", mcpName='" + mcpName + '\'' +
                '}';
    }
}
