package ru.job4j.concurrent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public final class SaveContent {

    private final File file;

    public SaveContent(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), Charset.forName("UTF-8"))) {
            writer.write(content, 0, content.length());
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
