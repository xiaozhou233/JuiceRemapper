package cn.xiaozhou233.juiceremapper.remapper;

import cn.xiaozhou233.juiceremapper.mappings.MappingReader;
import lombok.Setter;
import org.objectweb.asm.ClassReader;

import java.util.HashMap;
import java.util.Map;

public class InheritanceMap {

    private final Map<String, String> superMap = new HashMap<>();
    @Setter
    private MappingReader mappings;

    public void loadClass(byte[] bytes) {
        ClassReader cr = new ClassReader(bytes);

        String name = cr.getClassName();
        String superName = cr.getSuperName();

        if(superName != null) {
            superMap.put(name, superName);
        }
    }

    public String getSuper(String cls) {
        String result = superMap.get(cls);

        if (result == null && mappings != null) {
            String obf = mappings.unmapClass(cls);
            if (obf != null) {
                result = superMap.get(obf);
                if (result != null) {
                    String mcpSuper = mappings.mapClass(result);
                    if (mcpSuper != null) {
                        result = mcpSuper;
                    }
                }
            }
        }
        return result;
    }

    public void put(String cls, String superCls) {
        superMap.put(cls, superCls);
    }

}