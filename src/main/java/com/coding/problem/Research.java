package com.coding.problem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by U8016976 on 23-Dec-2016.
 */
public class Research {

    public static void main(String[] args) throws IOException {

        String urlString = "http://more-sky.com/data/out/9/IMG_345056.jpg";
        URL url = new URL(urlString);

        System.out.println(getFileSize(url));

        String fileName = getFileName(url);

        saveUrl(fileName, urlString);

        System.out.println("Free disk " + new File("/").getFreeSpace());

        long heapFreeSize = Runtime.getRuntime().freeMemory();

        System.out.println("Heap free size : " + heapFreeSize);
    }

    private static String getFileName(URL url) {
        String[] filePathList = url.getFile().split("/");
        int indexOfFileName = (filePathList.length > 0 ? (filePathList.length -1) : 0);
        return filePathList[indexOfFileName];
    }

    private static int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }

    public static void saveUrl(final String filename, final String urlString) throws IOException {
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new URL(urlString).openStream());
            fileOutputStream = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            int size = 0;
            while ((count = bufferedInputStream.read(data, 0, 1024)) != -1) {
                size += count;
                System.out.print("\r = " + size);

                fileOutputStream.write(data, 0, count);
            }
            System.out.println("\nDownload "+ filename +" completed.");
        } finally {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }
}