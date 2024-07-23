package org.api.models.api;

import java.util.Map;

public class HttpRequest {

    String url;
    String body;
    Map<String ,String> header;
    HttpMethod method;

    public HttpRequest(HttpMethod method, String url, String body, Map<String,String> header){
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

    public HttpMethod getMethod() {
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

    public void setMethod(HttpMethod method) {
        this.method = method;
    }
}
