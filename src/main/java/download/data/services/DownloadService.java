package download.data.services;


import download.data.exception.FileAlreadyDownloadException;
import download.data.exception.LessMemoryException;
import download.data.utility.DownloadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class DownloadService {

    protected static Logger log = LoggerFactory.getLogger(DownloadService.class);

    private String url;

    private String targetLocation;

    private String fileName;

    private int lenghtOfFile;

    protected abstract int getFileSize();

    public boolean handler() {
        lenghtOfFile = getFileSize();
        long freeMemory = getFreeMemory();
        if (lenghtOfFile > freeMemory) {
            log.error("=============== ERROR ================");
            log.error("Memory is not enough to save the file.");
            log.error("File size is : {} ", lenghtOfFile);
            log.error("Memory available size is : {} ", freeMemory);
            throw new LessMemoryException("Memory is not enough");
        }

        if (alreadyDownload()) {
            log.error("=============== ERROR ================");
            log.error("File already download : {} ", url);
            throw new FileAlreadyDownloadException("File " + url + " : already download.");
        }

        return download();
    }

    protected abstract boolean download();

    protected void updateProgress(int currentDownloadSize) {
        int downloadingPercentage = DownloadFileUtil.getInstance().getDownloadPercentage(currentDownloadSize, lenghtOfFile);
        System.out.print("\r Downloading " + getFileName() + " " + downloadingPercentage + "% (" + currentDownloadSize + " of " + lenghtOfFile + " bytes), [Remaining memory size = " + getFreeMemory() + "]");
    }

    private boolean alreadyDownload() {
        return false;
    }

    public long getFreeMemory() {
      return new File("/").getFreeSpace();
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