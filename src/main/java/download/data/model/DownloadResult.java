package download.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Somjade on 29-Dec-2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadResult {
    private String source;
    private String destination;
    private boolean success;
    private String userMessage;
    private String developerMessage;

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("destination")
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @JsonProperty("isSuccess")
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @JsonProperty("userMessage")
    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @JsonProperty("developerMessage")
    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    @Override
    public String toString() {
        return "DownloadResult{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", success=" + success +
                ", userMessage='" + userMessage + '\'' +
                ", developerMessage='" + developerMessage + '\'' +
                '}';
    }
}