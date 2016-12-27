package download.data.controller;

import download.data.model.RequestPayload;
import download.data.services.DownloadService;
import download.data.services.DownloadServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Value("${download.file.location}")
    private String targetLocation;

    @Autowired
    private DownloadServiceFactory downloadServiceFactory;

    @RequestMapping(value = "/download", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestBody RequestPayload requestPayload)
            throws InterruptedException {

        DownloadService downloadService = downloadServiceFactory.getDownLoadService(requestPayload.getUrl());
        downloadService.setUrl(requestPayload.getUrl());
        downloadService.setTargetLocation(targetLocation);
        downloadService.setFileName(requestPayload.getFileName());
        try {
             downloadService.handler();
             System.out.println();
             System.out.println("!!!!!!!!!! Download completed !!!!!!!!!");
            return true;
        } catch (Exception ex) {
            System.out.println();
            System.out.println("************ Download failed : " + ex.getMessage() + " *************");
            return false;
        }

    }
}
