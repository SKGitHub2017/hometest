package download.data.services;


import download.data.exception.FileAlreadyDownloadException;
import download.data.exception.LessMemoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class DownloadService {

    protected static Logger log = LoggerFactory.getLogger(DownloadService.class);

    private String url;

    private String targetLocation;

    private String fileName;

    private int fileSize;

    protected abstract int getFileSize();

    public boolean handler() {
        fileSize = getFileSize();
        long freeMemory = getFreeMemory();
        if (fileSize > freeMemory) {
            log.error("=============== ERROR ================");
            log.error("Memory is not enough to save the file.");
            log.error("File size is : {} ", fileSize);
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

        System.out.print("\r Download status :  " +  currentDownloadSize + " of " + fileSize + " bytes. [Remaining memory size = " + getFreeMemory() + "]");
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
        final File targetDirectory = new File(targetLocation);
        if(!targetDirectory.exists()) {
            boolean result = targetDirectory.mkdir();
            if(result) {
                System.out.println("The directory \"" + targetLocation + "\" is created !");
            }
        } else {
            System.out.println("The directory \"" + targetLocation + "\" already exist");
        }
        this.targetLocation = targetLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}