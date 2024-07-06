package org.api.clients;
import okhttp3.*;
import org.api.Main;
import org.api.models.HttpRequest;

import java.io.IOException;
import java.util.Map;

public class RestClient{
    OkHttpClient client;

    public RestClient() {

        this.client=new OkHttpClient();
    }

    public Response executeRequest(HttpRequest httpRequest) throws HttpRequestException, IOException {
        Request request;
        try {
            if(httpRequest.getMethod().equalsIgnoreCase("Post") || httpRequest.getMethod().equalsIgnoreCase("Put")) {
                MediaType header=MediaType.get(httpRequest.getHeader().get("content-type"));
                if(header==null) throw new IllegalArgumentException("content-type header is missing");
                RequestBody body = RequestBody.create(header,httpRequest.getBody());
                request = new Request.Builder()
                        .method(httpRequest.getMethod(),body)
                        .url(httpRequest.getUrl())
                        .build();
            }
            else {
            request = new Request.Builder()
                    .url(httpRequest.getUrl())
                    .build();
            }
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Error building Http request"+e.getMessage());
        }
        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()){
            throw new HttpRequestException("Http request failed with status code"+ response.code());
        }
        return response  ;
    }

    public class HttpRequestException extends Exception{
        public HttpRequestException(String message){
            super(message);
        }
    }


}
