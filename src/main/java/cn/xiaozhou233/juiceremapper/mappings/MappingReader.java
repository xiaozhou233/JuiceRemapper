package cn.xiaozhou233.juiceremapper.mappings;

import cn.xiaozhou233.juiceremapper.mappings.beans.MethodBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

public class MappingReader {

    // =========================
    // Class mappings
    // obf -> mcp
    // =========================
    private final HashMap<String, String> classMap = new HashMap<>();
    private final HashMap<String, String> classMapReverse = new HashMap<>();

    // =========================
    // Method mappings
    // key: owner/name desc
    // =========================
    private final HashMap<String, MethodBean> methodMap = new HashMap<>();
    private final HashMap<String, MethodBean> methodMapReverse = new HashMap<>();

    // =========================
    // Field mappings
    // key: owner/name
    // =========================
    private final HashMap<String, String> fieldMap = new HashMap<>();
    private final HashMap<String, String> fieldMapReverse = new HashMap<>();

    private final String path;

    public MappingReader(MappingVersion version) {

        if (Objects.requireNonNull(version) == MappingVersion.V1_8_9) {
            path = "/mappings/1.8.9/vanilla.srg";
        } else {
            throw new IllegalArgumentException("Unsupported mapping version");
        }

        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream == null) {
            throw new RuntimeException("Mapping file not found: " + path);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isEmpty()) continue;

                String[] split = line.split(" ");

                switch (split[0]) {

                    case "CL:":
                        String obfClass = split[1];
                        String mcpClass = split[2];

                        classMap.put(obfClass, mcpClass);
                        classMapReverse.put(mcpClass, obfClass);
                        break;

                    case "MD:":
                        MethodBean bean = new MethodBean(
                                split[1],
                                split[2],
                                split[3],
                                split[4]
                        );

                        String obfKey = split[1] + " " + split[2];
                        String mcpKey = split[3] + " " + split[4];

                        methodMap.put(obfKey, bean);
                        methodMapReverse.put(mcpKey, bean);
                        break;

                    case "FD:":
                        String obfField = split[1];
                        String mcpField = split[2];

                        fieldMap.put(obfField, mcpField);
                        fieldMapReverse.put(mcpField, obfField);
                        break;

                    default:
                        System.out.println("Unknown mapping type: " + split[0]);
                        break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read mapping file", e);
        }
    }

    // =========================================================
    // API
    // =========================================================

    // Class
    public String mapClass(String obf) {
        return classMap.getOrDefault(obf, obf);
    }

    public String unmapClass(String mcp) {
        return classMapReverse.getOrDefault(mcp, mcp);
    }

    // Field
    public String mapField(String owner, String name) {
        return fieldMap.getOrDefault(owner + "/" + name, name);
    }

    public String unmapField(String owner, String name) {
        return fieldMapReverse.getOrDefault(owner + "/" + name, name);
    }

    // Method
    public MethodBean mapMethod(String owner, String name, String desc) {
        return methodMap.get(owner + "/" + name + " " + desc);
    }

    public MethodBean unmapMethod(String owner, String name, String desc) {
        return methodMapReverse.get(owner + "/" + name + " " + desc);
    }
}