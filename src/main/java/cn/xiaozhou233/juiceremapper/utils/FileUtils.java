package cn.xiaozhou233.juiceremapper.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    public static String readStream(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        try (InputStream is = inputStream;
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            byte[] data = new byte[4096];
            int nRead;

            while ((nRead = is.read(data)) != -1) {
                buffer.write(data, 0, nRead);
            }

            return new String(buffer.toByteArray(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read stream", e);
        }
    }
}