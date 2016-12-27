package download.data.services;

import download.data.exception.DownloadServiceException;
import download.data.services.implementation.*;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class DownloadServiceFactory {

    public DownloadService getDownLoadService(String url) {

        try {
            URI uri = new URI(url);
            switch (uri.getScheme().toLowerCase()) {
                case "http": return new HttpDownloadService();
                case "ftp": return new FtpDownloadService();
                case "sftp": return new SftpDownloadService();
            }
        } catch (URISyntaxException uriEx) {
            throw new DownloadServiceException(uriEx.getMessage());
        }
        return null;
    }
}