package download.data.services.implementation;

import download.data.services.DownloadService;


public class SftpDownloadService extends DownloadService {
    @Override
    protected int getFileSize() {
        return 0;
    }

    @Override
    protected boolean download() {
        return false;
    }
}
