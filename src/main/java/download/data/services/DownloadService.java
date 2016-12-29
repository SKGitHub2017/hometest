package download.data.services;


import download.data.exception.FileAlreadyDownloadException;
import download.data.exception.NotEnoughDiskSpaceException;
import download.data.exception.NotEnoughMemoryException;
import download.data.utility.DownloadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.MessageFormat;

public abstract class DownloadService {

    protected static Logger log = LoggerFactory.getLogger(DownloadService.class);

    //private static Map<String, String> synchronizedMap = Collections.synchronizedMap(new HashMap<String, String>());

    private String url;

    private String targetLocation;

    private String fileName;

    private int lenghtOfFile;

    protected abstract int getFileSize();

    public boolean handler() {

        lenghtOfFile = getFileSize();

        long freeMemoryInBytes = DownloadFileUtil.getInstance().getfreeMemoryInBytes();
        if (lenghtOfFile > freeMemoryInBytes) {
            log.error("=============== ERROR ================");
            log.error("Memory is not enough to save the file.");
            log.error("File size is : {} ", lenghtOfFile);
            log.error("Memory available size is : {} bytes", freeMemoryInBytes);
            throw new NotEnoughMemoryException("Memory is not enough");
        }

        long freeDiskSpaceInBytes = DownloadFileUtil.getInstance().getfreeSpaceInBytes(getTargetLocation());
        if (lenghtOfFile > freeDiskSpaceInBytes) {
            log.error("=============== ERROR ================");
            log.error("Disk free space (unused) is not enough to save the file.");
            log.error("File size is : {} ", lenghtOfFile);
            log.error("Disk space available size is : {} bytes", freeDiskSpaceInBytes);
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
        DownloadFileUtil downloadFileUtil = DownloadFileUtil.getInstance();
        int downloadingPercentage = downloadFileUtil.getDownloadPercentage(currentDownloadSize, lenghtOfFile);

        System.out.print(MessageFormat.format("\r Downloading from {0} to {1} , {2}% [{3} of {4}], [Disk space left (unused) {5}, JVM heap memory size left (unused) {6}]",
                getUrl(),
                downloadFileUtil.getFullPath(getTargetLocation(), getFileName()),
                downloadingPercentage,
                downloadFileUtil.formatSizeFromByteToHumanReadable(currentDownloadSize),
                downloadFileUtil.formatSizeFromByteToHumanReadable(lenghtOfFile),
                downloadFileUtil.formatSizeFromByteToHumanReadable(downloadFileUtil.getfreeSpaceInBytes(getTargetLocation())),
                downloadFileUtil.formatSizeFromByteToHumanReadable(downloadFileUtil.getfreeMemoryInBytes())));
    }

    private boolean alreadyDownload() {
/*
        String key = DownloadFileUtil.getInstance().generateMD5(getFileName());
        synchronized (synchronizedMap) {
            if (synchronizedMap.containsKey(key)) {
                return true;
            }
            synchronizedMap.put(key, getUrl());
        }
        return false;*/
        String targetFile = DownloadFileUtil.getInstance().getFullPath(getTargetLocation(), getFileName());
        File downloadFile = new File(targetFile);
        return downloadFile.exists();
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