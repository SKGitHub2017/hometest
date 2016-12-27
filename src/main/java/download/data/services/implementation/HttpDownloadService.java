package download.data.services.implementation;

import download.data.exception.DownloadServiceException;
import download.data.services.DownloadService;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpDownloadService extends DownloadService {

    @Override
    public int getFileSize()  {
        HttpURLConnection conn = null;
        try {
            URL url  = new URL(getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            int size = conn.getContentLength();
            if (size <= 0 ) {
                log.error("=============== ERROR ================");
                log.error("Can not get file size from : {} ", getUrl());
                throw new DownloadServiceException("Can not get file size from " + getUrl());
            }
            return size;
        } catch (IOException e) {
            throw new DownloadServiceException(e.getMessage());
        } finally {
            conn.disconnect();
        }
    }

    @Override
    protected boolean download() {
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new URL(getUrl()).openStream());
            fileOutputStream = new FileOutputStream(getTargetLocation() + "/" + getFileName());

            final byte data[] = new byte[1024];
            int count;
            int currentDownloadSize = 0;
            while ((count = bufferedInputStream.read(data, 0, 1024)) != -1) {
                currentDownloadSize += count;
                updateProgress(currentDownloadSize);
                fileOutputStream.write(data, 0, count);
            }
            return true;
        }catch (Exception ex) {
            log.error("=============== ERROR ================");
            log.error("Download failed  : {} ", ex.getMessage());
            throw new DownloadServiceException("Download failed " + ex.getMessage());
        } finally {

            try {
                bufferedInputStream.close();
            } catch (IOException e) {
                //
            }

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                //
            }

        }
    }
}
