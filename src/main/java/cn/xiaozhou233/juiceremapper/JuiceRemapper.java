package cn.xiaozhou233.juiceremapper;

import cn.xiaozhou233.juiceremapper.mappings.MappingReader;
import cn.xiaozhou233.juiceremapper.mappings.MappingVersion;
import cn.xiaozhou233.juiceremapper.mappings.beans.ClassBean;
import cn.xiaozhou233.juiceremapper.remapper.InheritanceMap;
import cn.xiaozhou233.juiceremapper.utils.IOUtils;

import java.io.InputStream;

public class JuiceRemapper {
    public static MappingReader reader;
    public static InheritanceMap inheritance;

    public static boolean init() {
        System.out.println("Init JuiceRemapper...");
        reader = new MappingReader(MappingVersion.V1_8_9);
        inheritance = new InheritanceMap();
        inheritance.setMappings(reader);

        System.out.println("[JuiceRemapper] Pre-loading class inheritance...");
        int loaded = 0;
        for (ClassBean bean : reader.getTable().getClasses()) {
            String obf = bean.getObfuscatedName();
            String resource = "/" + obf + ".class";
            try (InputStream is = JuiceRemapper.class.getResourceAsStream(resource)) {
                if (is != null) {
                    byte[] bytes = IOUtils.toByteArray(is);
                    if (bytes.length > 0) {
                        inheritance.loadClass(bytes);
                        loaded++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("[JuiceRemapper] Loaded " + loaded + " class hierarchies");

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