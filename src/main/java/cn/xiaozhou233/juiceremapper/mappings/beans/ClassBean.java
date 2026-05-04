package cn.xiaozhou233.juiceremapper.mappings.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassBean {
    public String obfuscatedName;
    public String name;

    public ClassBean(String obfuscatedName, String name) {
        this.name = name;
        this.obfuscatedName = obfuscatedName;
    }
}
