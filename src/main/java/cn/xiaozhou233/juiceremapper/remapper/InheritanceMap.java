package cn.xiaozhou233.juiceremapper.remapper;

import org.objectweb.asm.ClassReader;

import java.util.HashMap;
import java.util.Map;

public class InheritanceMap {

    private final Map<String, String> superMap = new HashMap<>();

    public void loadClass(byte[] bytes) {
        ClassReader cr = new ClassReader(bytes);

        String name = cr.getClassName();
        String superName = cr.getSuperName();

        if (superName != null) {
            superMap.put(name, superName);
        }
    }

    public String getSuper(String cls) {
        return superMap.get(cls);
    }
}