package cn.xiaozhou233.juiceremapper.mappings;

import cn.xiaozhou233.juiceremapper.mappings.beans.ClassBean;
import cn.xiaozhou233.juiceremapper.mappings.beans.FieldBean;
import cn.xiaozhou233.juiceremapper.mappings.beans.MethodBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class MappingReader {

    private final MappingTable table = new MappingTable();

    public MappingReader(MappingVersion version) {
        this(version, null);
    }

    /**
     * @param version mapping version
     * @param filePath optional SRG file path, falls back to classpath resource
     *                 (e.g. E:\JuiceRemapper\src\main\resources\mappings\1.8.9\vanilla.srg.txt)
     */
    public MappingReader(MappingVersion version, String filePath) {
        Objects.requireNonNull(version, "version must not be null");

        String resourcePath;
        switch (version) {
            case V1_8_9:
                resourcePath = "/mappings/1.8.9/vanilla.srg";
                break;
            default:
                throw new IllegalArgumentException("Unsupported mapping version: " + version);
        }

        try {
            if (filePath != null && !filePath.isEmpty() && Files.exists(Paths.get(filePath))) {
                read(version, Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8));
            } else {
                InputStream inputStream = getClass().getResourceAsStream(resourcePath);
                if (inputStream == null) {
                    throw new RuntimeException("Mapping file not found: " + resourcePath);
                }
                read(version, new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read mapping file", e);
        }

        System.out.println("[JuiceRemapper] Mapping Loaded!");
        System.out.println("[JuiceRemapper] Class: " + table.classCount());
        System.out.println("[JuiceRemapper] Method: " + table.methodCount());
        System.out.println("[JuiceRemapper] Field: " + table.fieldCount());
    }

    private void read(MappingVersion version, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) continue;

            String[] split = line.split(" ");

            switch (split[0]) {
                case "CL:":
                    table.addClass(new ClassBean(split[1], split[2]));
                    break;

                case "MD:":
                    table.addMethod(new MethodBean(split[1], split[2], split[3], split[4]));
                    break;

                case "FD:":
                    table.addField(splitField(split[1], split[2]));
                    break;

                default:
                    System.out.println("Unknown mapping type: " + split[0]);
                    break;
            }
        }
    }

    private FieldBean splitField(String obf, String mcp) {
        String obfOwner = obf.substring(0, obf.lastIndexOf("/"));
        String obfName = obf.substring(obf.lastIndexOf("/") + 1);
        String mcpOwner = mcp.substring(0, mcp.lastIndexOf("/"));
        String mcpName = mcp.substring(mcp.lastIndexOf("/") + 1);
        return new FieldBean(obfOwner, obfName, mcpOwner, mcpName);
    }

    // =========================================================
    // API (delegates to MappingTable)
    // =========================================================

    public MappingTable getTable() {
        return table;
    }

    // Class
    public String mapClass(String obf) {
        return table.mapClass(obf);
    }

    public String unmapClass(String mcp) {
        return table.unmapClass(mcp);
    }

    // Field
    public String mapField(String owner, String name) {
        return table.mapField(owner, name);
    }

    public String unmapField(String owner, String name) {
        return table.unmapField(owner, name);
    }

    // Method
    public MethodBean mapMethod(String owner, String name, String desc) {
        return table.getMethodByObf(owner, name, desc);
    }

    public MethodBean unmapMethod(String owner, String name, String desc) {
        return table.getMethodByMcp(owner, name, desc);
    }

    public static void main(String[] args) {
        new MappingReader(MappingVersion.V1_8_9);
    }
}
