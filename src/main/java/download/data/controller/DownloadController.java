package download.data.controller;

import download.data.model.RequestPayload;
import download.data.services.DownloadService;
import download.data.services.DownloadServiceFactory;
import download.data.utility.DownloadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class DownloadController {

    @Value("${download.file.location}")
    private String targetLocation;

    @Autowired
    private DownloadServiceFactory downloadServiceFactory;

    @Autowired
    private DownloadFileUtil downloadUtil;
    /*
        {
        "urls" : "http://weknowyourdreams.com/images/sea/sea-01.jpg, http://weknowyourdreams.com/images/sea/sea-02.jpg, ftp://other.file.com/other, sftp://and.also.this/ending",
        }
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean download(@RequestBody RequestPayload requestPayload)
            throws InterruptedException {

        // Get URLs list from input
        String[] urls = downloadUtil.getURLs(requestPayload.getUrl());
        // Ensure download target location exist.
        downloadUtil.ensureDirectory(targetLocation);
        // Loop url to download content
        for (String url: urls) {
            try {

                DownloadService downloadService = downloadServiceFactory.getDownLoadService(url);
                String fileName = downloadUtil.extractFileNameFromURL(url);

                if (!url.isEmpty() && !fileName.isEmpty()) {

                    downloadService.setUrl(url);
                    downloadService.setFileName(fileName);
                    downloadService.setTargetLocation(targetLocation);

                    // Process download content
                    downloadService.handler();
                    System.out.println("!!!!!!!!!! Download completed !!!!!!!!!");
                }
            } catch (Exception ex) {
                System.out.println("************ Download failed : " + ex.getMessage() + " *************");
            }
        }
        return false;
    }
}