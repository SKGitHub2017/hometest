package download.data.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class RequestPayload {

    @JsonProperty("url")
    @SerializedName("url")
     private String url;

    @JsonProperty("file_name")
    @SerializedName("file_name")
    private String fileName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "RequestPayload{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
