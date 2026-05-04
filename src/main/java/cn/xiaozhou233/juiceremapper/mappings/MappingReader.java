package cn.xiaozhou233.juiceremapper.mappings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MappingReader {
    private String path;

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
                String[] split = line.split(" ");
                switch (split[0]) {
                    case "CL:" :
                        // TODO: implement class mapping
                        break;
                    case "MD:" :
                        // TODO: implement method mapping
                        break;
                    case "FD:" :
                        // TODO: implement field mapping
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

    public static void main(String[] args) {
        new MappingReader(MappingVersion.V1_8_9);
    }
}