package pojo;

import lombok.Data;

@Data
public class RequestDetails {
    private String baseUri;
    private String basePath;
    private String method;
    private String body;

    @Override
    public String toString() {
        return "RequestDetails{" +
                "baseUri='" + baseUri + '\'' +
                ", basePath='" + basePath + '\'' +
                ", method='" + method + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
