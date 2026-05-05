package cn.xiaozhou233.juiceremapper.remapper;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;

public class RemapUtils {

    public static byte[] remap(byte[] bytes, HierarchyRemapper remapper) {
        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassRemapper visitor = new ClassRemapper(cw, remapper);

        cr.accept(visitor, ClassReader.EXPAND_FRAMES);

        return cw.toByteArray();
    }

}