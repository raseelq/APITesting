package org.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.clients.RestClient;
import org.api.constants.Constants;
import org.api.models.api.HTTPResponse;
import org.api.models.api.HttpMethod;
import org.api.models.api.HttpRequest;
import org.api.models.api.TokenResponse;
import org.api.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginService {
    HttpRequest request;
    HTTPResponse response;
    ObjectMapper mapper=new ObjectMapper();
    RestClient client=new RestClient();
    /**
     * Logs in a user by sending their username and password to the authentication endpoint.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return HTTPResponse The response from the server.
     * @throws IOException If there is an I/O error during the request.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    public HTTPResponse Login(String username, String password) throws IOException, RestClient.HttpRequestException {
        Map<String,String> body=new HashMap<>();
        body.put("username",username);
        body.put("password",password);
        // Create the JSON header
        Map<String,String> jsonHeader=Utils.createJsonHeader();

        // Create an HttpRequest object and execute request
        request=new HttpRequest(HttpMethod.POST, Constants.BASE_URL+Constants.LOGIN_ENDPOINT,mapper.writeValueAsString(body),jsonHeader);
        return Utils.mapResponseToObject(client.executeRequest(request), HTTPResponse.class);

    }
}
