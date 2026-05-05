package cn.xiaozhou233.juiceremapper.remapper;

import cn.xiaozhou233.juiceremapper.mappings.MappingReader;
import cn.xiaozhou233.juiceremapper.mappings.beans.MethodBean;
import org.objectweb.asm.commons.Remapper;

public class SrgRemapper extends Remapper {

    private final MappingReader mappings;

    public SrgRemapper(MappingReader mappings) {
        this.mappings = mappings;
    }

    // =========================
    // Class
    // =========================
    @Override
    public String map(String internalName) {
        return mappings.mapClass(internalName);
    }

    // =========================
    // Field
    // =========================
    @Override
    public String mapFieldName(String owner, String name, String descriptor) {
        String mapped = mappings.mapField(owner, name);

        if (mapped == null) return name;

        return mapped.substring(mapped.lastIndexOf("/") + 1);
    }

    // =========================
    // Method
    // =========================
    @Override
    public String mapMethodName(String owner, String name, String descriptor) {
        MethodBean m = mappings.mapMethod(owner, name, descriptor);

        if (m == null) return name;

        String full = m.getFullMcpName();
        return full.substring(full.lastIndexOf("/") + 1);
    }

    // =========================
    // Descriptor（关键）
    // =========================
    @Override
    public String mapDesc(String descriptor) {
        return super.mapDesc(descriptor);
    }

    @Override
    public String mapMethodDesc(String methodDescriptor) {
        return super.mapMethodDesc(methodDescriptor);
    }
}