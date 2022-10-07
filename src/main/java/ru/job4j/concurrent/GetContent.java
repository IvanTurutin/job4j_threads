package ru.job4j.concurrent;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.function.Predicate;


public final class GetContent {

	private final File file;

    public GetContent(File file) {
		this.file = file;
	}

    public String getContent() throws IOException {
		Predicate<Integer> filter = x -> true;
		return readTextFile(filter);
    }
	
    public String getContentWithoutUnicode() throws IOException {
		Predicate<Integer> filter = x -> x < 0x80;
		return readTextFile(filter);
    }

	private String readTextFile(Predicate<Integer> filter) throws IOException {
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
}
