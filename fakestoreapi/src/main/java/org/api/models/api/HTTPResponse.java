package org.api.models;

public class HTTPResponse {
    String body;
    int code;

    public HTTPResponse() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
