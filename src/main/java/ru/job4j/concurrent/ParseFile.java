package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;


public final class ParseFile {

	private final File file;

    public ParseFile(File file) {
		this.file = file;
	}

    public String getContent() throws IOException {
		Predicate<Integer> filter = x -> true;
		return ReadWriteText.readTextFile(file, filter);
    }
	
    public String getContentWithoutUnicode() throws IOException {
		Predicate<Integer> filter = x -> x < 0x80;
		return ReadWriteText.readTextFile(file, filter);
    }

	public void saveContent(String content) throws IOException {
		ReadWriteText.writeTextFile(file, content);
	}
}
