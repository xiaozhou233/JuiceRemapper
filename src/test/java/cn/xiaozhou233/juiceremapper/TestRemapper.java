package cn.xiaozhou233.juiceremapper;

import cn.xiaozhou233.juiceremapper.mappings.MappingReader;
import cn.xiaozhou233.juiceremapper.mappings.MappingVersion;
import cn.xiaozhou233.juiceremapper.remapper.*;
import cn.xiaozhou233.juiceremapper.utils.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;

public class TestRemapper {
    public static void main(String[] args) throws IOException {
        MappingReader reader = new MappingReader(MappingVersion.V1_8_9);

        InheritanceMap inheritance = new InheritanceMap();

        byte[] originalBytes = IOUtils.toByteArray(TestRemapper.class.getResourceAsStream("/OrangeX.class"));

        inheritance.loadClass(originalBytes);

        BiRemapper remapper = new BiRemapper(
                reader,
                inheritance,
                RemapMode.MCP_TO_OBF
        );

        byte[] newBytes = RemapUtils.remap(originalBytes, remapper);

        try(FileOutputStream fos = new FileOutputStream("OrangeX_new.class")) {
            fos.write(newBytes);
        }
    }
}
