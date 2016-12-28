package download.data.utility;

import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by U8016976 on 28-Dec-2016.
 */
@Service
public class DownloadFileUtil {
    private static DownloadFileUtil ourInstance = new DownloadFileUtil();

    public static DownloadFileUtil getInstance() {
        return ourInstance;
    }

    private DownloadFileUtil() {
    }

    public int getDownloadPercentage(int currentDownloadSize, int lenghtOfFile){
        return ((currentDownloadSize*100)/lenghtOfFile);
    }

    public boolean ensureDirectory(File dir) {
        if (dir == null) {
            throw new IllegalArgumentException("dir = null");
        }
        boolean success = true;
        if (!dir.isDirectory()) {
            success = !dir.isFile() && dir.mkdirs();

            if (success) {
                System.out.println("Created directory: " + dir.toString());
            } else {
                System.out.println("failed while trying to create directory: " + dir.toString());
            }
        }

        return success;
    }

    public boolean ensureDirectory(String path) {
        return ensureDirectory(new File(path));
    }

    public String extractFileNameFromURL(String url){
        return url.substring(url.lastIndexOf('/') + 1);
    }

    public String generateMD5(String str) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return "";
    }

    public String[] getURLs(String inputURLs){
        if (!inputURLs.isEmpty())
            return inputURLs.split( ",\\s*" ); // split on commas
        return null;
    }
}