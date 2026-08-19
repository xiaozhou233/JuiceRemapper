package cn.xiaozhou233.juiceremapper.remapper;

import cn.xiaozhou233.juiceremapper.JuiceRemapper;

public class Remap {
    public static byte[] remap(String className, byte[] classBytes) {
        System.out.println("[Debug] JuiceRemapper: Remapping " + className);

        JuiceRemapper.inheritance.loadClass(classBytes);
        BiRemapper remapper = new BiRemapper(
                JuiceRemapper.reader,
                JuiceRemapper.inheritance,
                RemapMode.MCP_TO_OBF
        );


        return RemapUtils.remap(classBytes, remapper);
    }
}
