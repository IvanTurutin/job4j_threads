package ru.job4j.concurrent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.function.Predicate;

public class ReadWriteText {

    public static String readTextFile(File file, Predicate<Integer> filter) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), Charset.forName("UTF-8"))) {
            int data;
            while ((data = reader.read()) > 0) {
                if (filter.test(data)) {
                    sb.append(data);
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return sb.toString();
    }

    public static void writeTextFile(File file, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), Charset.forName("UTF-8"))) {
            writer.write(content, 0, content.length());
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
