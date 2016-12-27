package download.data.controller;

import download.data.model.RequestPayload;
import download.data.services.DownloadService;
import download.data.services.DownloadServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.util.UUID;

@RestController
public class MainController {

    @Value("${download.file.location}")
    private String targetLocation;

    @Autowired
    private DownloadServiceFactory downloadServiceFactory;

    /*
        {
        "url" : "http://weknowyourdreams.com/images/sea/sea-01.jpg, http://weknowyourdreams.com/images/sea/sea-02.jpg, ftp://other.file.com/other, sftp://and.also.this/ending",
        "file_name" : "test3.jpg"
        }
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean download(@RequestBody RequestPayload requestPayload)
            throws InterruptedException {

        String[] urls = requestPayload.getUrl().split( ",\\s*" ); // split on commas

        final File targetDirectory = new File(targetLocation);
        if(!targetDirectory.exists()) {
            boolean result = targetDirectory.mkdir();
            if(result) {
                System.out.println("The directory \"" + targetLocation + "\" is created !");
            }
        } else {
            System.out.println("The directory \"" + targetLocation + "\" already exist");
        }

        for (String url: urls) {

            DownloadService downloadService = downloadServiceFactory.getDownLoadService(url);
            downloadService.setUrl(url);
            downloadService.setTargetLocation(targetLocation);
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            downloadService.setFileName(fileName);
            try {
                downloadService.handler();
                System.out.println();
                System.out.println("!!!!!!!!!! Download completed !!!!!!!!!");
            } catch (Exception ex) {
                System.out.println();
                System.out.println("************ Download failed : " + ex.getMessage() + " *************");
            }
        }
        return false;
    }
}
