package ru.job4j.concurrent;

import java.io.*;
import java.nio.*;
import java.util.function.Predicate;
import java.nio.file.Files;
import java.nio.charset.Charset;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
		this.file = file;
	}
	
	/*public synchronized void setFile(File f) {
        file = f;
    }*/
    
    /*public synchronized File getFile() {
        return file;
    }*/

    public String getContent() throws IOException {
		/*
		InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            output += (char) data;
        }*/
		Predicate<Integer> filter = x -> true;
        //return readFile(file.toPath(), Charset.forName("UTF-8"));
		return readFile(filter);
    }
	
	/*public String readFile(Path path, Charset charset) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new IOException(e);
		}
        return sb.toString();
	}*/
	
	public String readFile(Predicate<Integer> filter) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(file.toPath(), Charset.forName("UTF-8"))) {
			int data;
			while ((data = reader.read()) > 0) {
				if (filter.test(data)) {
					//output += (char) data;
					sb.append(data);
				}
			}
		} catch (IOException e) {
			throw new IOException(e);
		}
        return sb.toString();
	}
	

    public String getContentWithoutUnicode() throws IOException {
		/*InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;*/
		Predicate<Integer> filter = x -> x < 0x80;
		//return readFile(file.toPath(), Charset.forName("US-ASCII"));
		return readFile(filter);
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), Charset.forName("UTF-8"))) {
			writer.write(content, 0, content.length());
		} catch (IOException e) {
			throw new IOException(e);
		}
		/*
		OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
		*/
    }
}
