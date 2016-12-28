package download.data.services;


import download.data.exception.FileAlreadyDownloadException;
import download.data.exception.NotEnoughDiskSpaceException;
import download.data.exception.NotEnoughMemoryException;
import download.data.utility.DownloadFileUtil;
import org.apache.commons.io.FileSystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class DownloadService {

    protected static Logger log = LoggerFactory.getLogger(DownloadService.class);

    //private static Map<String, String> downloadedMap = new HashMap<>();
    private static Map<String, String> synchronizedMap = Collections.synchronizedMap(new HashMap<String, String>());

    private String url;

    private String targetLocation;

    private String fileName;

    private int lenghtOfFile;

    protected abstract int getFileSize();

    public boolean handler() {

        lenghtOfFile = getFileSize();

        long freeMemory = getfreeMemoryInByte();
        if (lenghtOfFile > freeMemory) {
            log.error("=============== ERROR ================");
            log.error("Memory is not enough to save the file.");
            log.error("File size is : {} ", lenghtOfFile);
            log.error("Memory available size is : {} ", freeMemory);
            throw new NotEnoughMemoryException("Memory is not enough");
        }

        long freeDiskSpace = getfreeSpaceInByte();
        //freeDiskSpace = 20;
        if (lenghtOfFile > freeDiskSpace) {
            log.error("=============== ERROR ================");
            log.error("Disk free space (unused) is not enough to save the file.");
            log.error("File size is : {} ", lenghtOfFile);
            log.error("Disk space available size is : {} ", freeDiskSpace);
            throw new NotEnoughDiskSpaceException("Disk space is not enough");
        }

        if (alreadyDownload()) {
            log.error("=============== ERROR ================");
            log.error("File Name: " + getFileName() + " already exists in target download location, The file name should be uniquely determined from the URL");
            throw new FileAlreadyDownloadException("File Name: " + getFileName() + " already exists in target download location");
        }

        return download();
    }

    protected abstract boolean download();

    protected void updateProgress(int currentDownloadSize) {
        int downloadingPercentage = DownloadFileUtil.getInstance().getDownloadPercentage(currentDownloadSize, lenghtOfFile);
        System.out.print("\r Downloading " + getFileName() + " " + downloadingPercentage + "% (" + currentDownloadSize + " of " + lenghtOfFile + " bytes), [Disk space left (unused) in byte = " + getfreeSpaceInByte() + ", JVM heap memory size left (unused) in byte = " + getfreeMemoryInByte() + "]");
    }

    private boolean alreadyDownload() {
        String key = DownloadFileUtil.getInstance().generateMD5(getFileName());
        synchronized (synchronizedMap) {
            if (synchronizedMap.containsKey(key)) {
                return true;
            }
            synchronizedMap.put(key, getUrl());
        }
        return false;
    }

    public long getfreeMemoryInByte() {
        //Returns the amount of free memory in the Java Virtual Machine.
        return Runtime.getRuntime().freeMemory();
    }

    public long getfreeSpaceInByte(){
        Path path = Paths.get(getTargetLocation());
        return new File(String.valueOf(path.getRoot())).getFreeSpace();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}