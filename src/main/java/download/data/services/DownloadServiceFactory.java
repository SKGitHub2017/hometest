package download.data.services;


import download.data.services.implementation.FtpDownloadService;
import download.data.services.implementation.HttpDownloadService;
import org.springframework.stereotype.Service;

@Service
public class DownloadServiceFactory {

    public DownloadService getDownLoadService(String url) {

        if (url.toLowerCase().trim().startsWith("http")) {
            return new HttpDownloadService();
        } else if (url.toLowerCase().trim().startsWith("ftp")) {
            return new FtpDownloadService();
        }
        return null;
    }
}