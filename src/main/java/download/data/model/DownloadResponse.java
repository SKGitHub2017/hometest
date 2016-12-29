package download.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somjade on 29-Dec-2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadResponse {

    private List<DownloadResult> results = new ArrayList<DownloadResult>();

    @JsonProperty("results")
    public List<DownloadResult> getResults() {
        return results;
    }

    public void setResults(List<DownloadResult> results) {
        this.results = results;
    }
}
