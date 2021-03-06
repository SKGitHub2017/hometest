package download.data.utility;

import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Somjade on 28-Dec-2016.
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

        if (url.isEmpty())
            return null;

        String[] tokens = url.split("[\\\\|/]");
        return tokens[tokens.length - 1];
        //return url.substring(url.lastIndexOf('/') + 1);
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

    public long getfreeMemoryInBytes() {
        //Returns the amount of free memory in the Java Virtual Machine.
        return Runtime.getRuntime().freeMemory();
    }

    public long getfreeSpaceInBytes(String pathName){
        Path path = Paths.get(pathName);
        return new File(String.valueOf(path.getRoot())).getFreeSpace();
    }

    public String formatSizeFromByteToHumanReadable(long bytes) {
        int u = 0;
        for (;bytes > 1024*1024; bytes >>= 10) {
            u++;
        }
        if (bytes > 1024)
            u++;
        return String.format("%.1f %cB", bytes/1024f, " kMGTPE".charAt(u));
    }

    public String getFullPath(String targetLocation, String fileName) {
        Path rootPath = Paths.get(targetLocation);
        Path partialPath = Paths.get(fileName);
        return rootPath.resolve(partialPath).toString();
    }
}