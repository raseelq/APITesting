package org.api.clients;
import okhttp3.*;
import org.api.models.api.HTTPResponse;
import org.api.models.api.HttpRequest;

import java.io.IOException;
import java.util.Map;

public class RestClient{
    // OkHttpClient instance to handle HTTP requests
    private final OkHttpClient client;

    public RestClient() {

        this.client=new OkHttpClient();
    }
    /**
     * Executes the given HTTP request and returns the response.
     *
     * @param httpRequest the HTTP request to be executed
     * @return the HTTP response
     * @throws IOException if an I/O error occurs
     */
    public HTTPResponse executeRequest(HttpRequest httpRequest) throws IOException {
        Request request=buildRequest(httpRequest);
        try(Response response=client.newCall(request).execute()){
        HTTPResponse httpResponse=new HTTPResponse();
        httpResponse.setBody(response.body().string());
        httpResponse.setCode(response.code());
        return  httpResponse;
        }
    }
    /**
     * Builds an OkHttp Request from the given HttpRequest object.
     * @param httpRequest the HTTP request to be converted
     * @return the built OkHttp Request
     */
    private Request buildRequest(HttpRequest httpRequest){
        Request request=null;
        try {
            Request.Builder builder = new Request.Builder()
                    .url(httpRequest.getUrl());
            String method = httpRequest.getMethod().toString();
            switch (method) {
                case "GET":
                case "DELETE":
                    request = builder.method(method, null).build();
                    break;
                case "PUT":
                case "POST":
                    MediaType mediaType = getMediaType(httpRequest.getHeader(),"content-type");
                    RequestBody body = RequestBody.create(mediaType, httpRequest.getBody());
                    request = builder.method(method, body).build();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported method " + method);
            }
        }catch(IllegalArgumentException e){
                throw new IllegalArgumentException("Error building Http request "+e.getMessage());
         }

        return request;
    }
    /**
     * Retrieves the MediaType from headers.
     * @param headers The map of headers.
     * @param key The key to look for in headers.
     * @return MediaType The MediaType corresponding to the header key.
     */
    private MediaType getMediaType(Map<String,String> headers, String key) {
        if(headers==null || !headers.containsKey(key) ){
            throw new IllegalArgumentException("header is missing or "+ key +" is missing from header");
        }
        return MediaType.get(headers.get(key));
    }
    // Custom exception class for HTTP request errors
    public static class HttpRequestException extends Exception{
        public HttpRequestException(String message){
            super(message);
        }
    }

}
