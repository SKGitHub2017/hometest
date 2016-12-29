package download.data.controller;

import download.data.Application;
import download.data.model.DownloadRequest;
import download.data.model.DownloadResponse;
import download.data.model.DownloadResult;
import download.data.services.DownloadService;
import download.data.services.DownloadServiceFactory;
import download.data.utility.DownloadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DownloadController {

    private Logger log = LoggerFactory.getLogger(Application.class);

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
    public @ResponseBody DownloadResponse download(@RequestBody DownloadRequest downloadRequest)
            throws InterruptedException {

        DownloadResponse response = new DownloadResponse();
        List<DownloadResult> results = new ArrayList<DownloadResult>();

        // Get URLs list from input
        String[] urls = downloadUtil.getURLs(downloadRequest.getUrl());
        // Ensure download target location exist.
        downloadUtil.ensureDirectory(targetLocation);
        // Loop url to download content

        DownloadResult result = null;
        String fileName = "";

        for (String url: urls) {

            try {

                DownloadService downloadService = downloadServiceFactory.getDownLoadService(url);
                fileName = downloadUtil.extractFileNameFromURL(url);

                if (!url.isEmpty() && !fileName.isEmpty()) {

                    downloadService.setUrl(url);
                    downloadService.setFileName(fileName);
                    downloadService.setTargetLocation(targetLocation);

                    // Process download content
                    downloadService.handler();
                    System.out.println("!!!!!!!!!! Download completed !!!!!!!!!");

                    result = new DownloadResult();
                    result.setSource(url);
                    result.setDestination(downloadUtil.getFullPath(targetLocation, fileName));
                    result.setSuccess(true);
                    result.setUserMessage("Download "+ url +" completed");
                    result.setDeveloperMessage("Download "+ url +" completed");
                    results.add(result);
                    log.info(result.toString());
                }
            } catch (Exception ex) {
                System.out.println("************ Download failed : " + ex.getMessage() + " *************");
                result = new DownloadResult();
                result.setSource(url);
                result.setDestination(downloadUtil.getFullPath(targetLocation, fileName));
                result.setSuccess(false);
                result.setUserMessage("Download "+ url +" failed");
                result.setDeveloperMessage("Download "+ url +" failed, " + ex.getMessage());
                results.add(result);
                log.error(result.toString(), ex);
            }
        }

        response.setResults(results);

        return response;
    }
}