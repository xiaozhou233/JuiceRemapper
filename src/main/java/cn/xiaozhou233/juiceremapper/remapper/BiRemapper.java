package cn.xiaozhou233.juiceremapper.remapper;

import cn.xiaozhou233.juiceremapper.mappings.MappingReader;
import cn.xiaozhou233.juiceremapper.mappings.beans.MethodBean;
import org.objectweb.asm.commons.Remapper;

public class BiRemapper extends Remapper {

    private final MappingReader mappings;
    private final InheritanceMap inheritance;
    private final RemapMode mode;

    public BiRemapper(MappingReader mappings,
                      InheritanceMap inheritance,
                      RemapMode mode) {
        this.mappings = mappings;
        this.inheritance = inheritance;
        this.mode = mode;
    }

    // =========================
    // Class
    // =========================
    @Override
    public String map(String internalName) {
        if (mode == RemapMode.OBF_TO_MCP) {
            return mappings.mapClass(internalName);
        } else {
            return mappings.unmapClass(internalName);
        }
    }

    // =========================
    // Field
    // =========================
    @Override
    public String mapFieldName(String owner, String name, String descriptor) {

        String current = owner;

        while (current != null) {

            String mapped;

            if (mode == RemapMode.OBF_TO_MCP) {
                mapped = mappings.mapField(current, name);
            } else {
                mapped = mappings.unmapField(current, name);
            }

            if (mapped != null) {
                return mapped.substring(mapped.lastIndexOf("/") + 1);
            }

            current = inheritance.getSuper(current);
        }

        return name;
    }

    // =========================
    // Method
    // =========================
    @Override
    public String mapMethodName(String owner, String name, String descriptor) {

        String current = owner;

        while (current != null) {

            MethodBean m;

            if (mode == RemapMode.OBF_TO_MCP) {
                m = mappings.mapMethod(current, name, descriptor);
            } else {
                m = mappings.unmapMethod(current, name, descriptor);
            }

            if (m != null) {
                String full = (mode == RemapMode.OBF_TO_MCP)
                        ? m.getFullMcpName()
                        : m.getFullObfuscatedName();

                return full.substring(full.lastIndexOf("/") + 1);
            }

            current = inheritance.getSuper(current);
        }

        return name;
    }

    // =========================
    // Descriptor
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