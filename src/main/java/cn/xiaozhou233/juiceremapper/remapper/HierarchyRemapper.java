package cn.xiaozhou233.juiceremapper.remapper;

import cn.xiaozhou233.juiceremapper.mappings.MappingReader;
import cn.xiaozhou233.juiceremapper.mappings.beans.MethodBean;
import org.objectweb.asm.commons.Remapper;

public class HierarchyRemapper extends Remapper {

    private final MappingReader mappings;
    private final InheritanceMap inheritance;

    public HierarchyRemapper(MappingReader mappings, InheritanceMap inheritance) {
        this.mappings = mappings;
        this.inheritance = inheritance;
    }

    @Override
    public String map(String internalName) {
        return mappings.mapClass(internalName);
    }

    @Override
    public String mapFieldName(String owner, String name, String descriptor) {

        String current = owner;

        while (current != null) {
            String mapped = mappings.mapField(current, name);
            if (mapped != null) {
                return mapped.substring(mapped.lastIndexOf("/") + 1);
            }
            current = inheritance.getSuper(current);
        }

        return name;
    }

    @Override
    public String mapMethodName(String owner, String name, String descriptor) {

        String current = owner;

        while (current != null) {
            MethodBean m = mappings.mapMethod(current, name, descriptor);

            if (m != null) {
                String full = m.getFullMcpName();
                return full.substring(full.lastIndexOf("/") + 1);
            }

            current = inheritance.getSuper(current);
        }

        return name;
    }
}