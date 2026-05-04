package cn.xiaozhou233.juiceremapper.mappings.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassBean {
    public String name;
    public String obfuscatedName;

    public ClassBean(String name, String obfuscatedName) {
        this.name = name;
        this.obfuscatedName = obfuscatedName;
    }
}
