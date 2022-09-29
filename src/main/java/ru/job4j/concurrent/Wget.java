package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * При запуске программы необходимо первым параметром ввести URL файла, вторым параметрам скорость скачивания в
 * байтах в секунду.
 */

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private static final Logger LOG = LoggerFactory.getLogger(Wget.class.getName());


    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String ls = System.lineSeparator();
        String fileName = "test_" + getFileName(url);
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long timeAfter;
            long timeAtLeast;
            long timeAddition;
            long timeBefore = System.currentTimeMillis();
            LOG.debug("timeBefore = {}", timeBefore);
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                timeAfter = System.currentTimeMillis();
                LOG.debug("timeAfter = {}", timeAfter);

                timeAtLeast = (long) ((double) bytesRead / speed * 1000);
                LOG.debug("timeAtLeast = {}", timeAtLeast);

                if (timeAtLeast > (timeAfter - timeBefore)) {
                    timeAddition = timeAtLeast - (timeAfter - timeBefore);
                } else {
                    timeAddition = 0;
                }
                LOG.debug("timeAddition = {}", timeAddition);

                try {
                    Thread.sleep(timeAddition);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                timeBefore = System.currentTimeMillis();
                LOG.debug("timeBefore = {}", timeBefore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(String url) {
        String[] splitURL = url.split("/");
        return splitURL[splitURL.length - 1];
    }

    public static void main(String[] args) throws InterruptedException {
        String url = validURL(args[0]);
        LOG.debug("URL = {}", url);
        int speed = validInt(args[1]);
        LOG.debug("speed = {}", speed);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    private static String validURL(String stringURL) {
        String rslt;
        try {
            rslt = new URL(stringURL).toString();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL has incorrect format");
        }
        return rslt;
    }

    private static int validInt(String stringInt) {
        int rslt;
        try {
            rslt = Integer.parseInt(stringInt);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enter speed in decimal format");
        }
        return rslt;
    }
}