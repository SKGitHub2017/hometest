package download.data.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class RequestPayload {

    @JsonProperty("urls")
    @SerializedName("urls")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RequestPayload{urls='" + url + "'}";
    }
}
