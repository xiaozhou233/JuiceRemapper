package cn.xiaozhou233.juiceremapper;

import cn.xiaozhou233.juiceremapper.mappings.MappingReader;
import cn.xiaozhou233.juiceremapper.mappings.MappingVersion;
import cn.xiaozhou233.juiceremapper.remapper.InheritanceMap;

public class JuiceRemapper {
    public static MappingReader reader;
    public static InheritanceMap inheritance;

    public static boolean init() {
        System.out.println("Init JuiceRemapper...");
        reader = new MappingReader(MappingVersion.V1_8_9);
        inheritance = new InheritanceMap();

        initNative();
        return true;
    }

    private native static boolean initNative();
    
    public native static boolean addInclude(String name);
    public native static boolean addExclude(String name);

    public static native boolean removeInclude(String name);
    public static native boolean removeExclude(String name);

    public static native void clearIncludes();
    public static native void clearExcludes();
}