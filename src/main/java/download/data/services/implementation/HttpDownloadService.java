package download.data.services.implementation;

import com.google.common.util.concurrent.RateLimiter;
import download.data.exception.DownloadServiceException;
import download.data.services.DownloadService;
import download.data.services.ThrottlingInputStream;
import org.apache.commons.io.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloadService extends DownloadService {

    @Override
    public int getFileSize()  {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(getUrl());
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
        //BufferedInputStream bufferedInputStream = null;
        ThrottlingInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        final RateLimiter throttler = RateLimiter.create(64 * FileUtils.ONE_KB);

        try {
            //bufferedInputStream = new BufferedInputStream(new URL(getUrl()).openStream());
            bufferedInputStream = new ThrottlingInputStream(new URL(getUrl()).openStream(), throttler);
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
            log.error("Download " + getFileName() + " failed  : {} ", ex.getMessage());
            throw new DownloadServiceException("Download " + getFileName() + " failed " + ex.getMessage());
        } finally {
            try {
                bufferedInputStream.close();
            } catch (IOException e) {
                log.error("Buffered input stream close error: {} ", e.getMessage());
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                log.error("File output stream close error: {} ", e.getMessage());
            }

        }
    }
}