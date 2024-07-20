package org.api.models;

import java.util.Map;

public class HttpRequest {

    String url;
    String body;
    Map<String ,String> header;
    String method;

    public HttpRequest(String method, String url, String body, Map<String,String> header){
        this.method=method;
        this.url=url;
        this.body=body;
        this.header=header;
    }
    //Getters
    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getMethod() {
        return method;
    }
    //Setters
    public void setUrl(String url) {
        this.url = url;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
