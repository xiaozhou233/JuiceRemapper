package cn.xiaozhou233.juiceremapper.mappings.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassBean {
    public String obfuscatedName;
    public String mcpName;

    public ClassBean(String obfuscatedName, String mcpName) {
        this.mcpName = mcpName;
        this.obfuscatedName = obfuscatedName;
    }
}
