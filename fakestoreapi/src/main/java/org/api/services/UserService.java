package org.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.clients.RestClient;
import org.api.constants.Constants;
import org.api.interfaces.IUserInterface;
import org.api.models.api.HttpMethod;
import org.api.models.api.HttpRequest;
import org.api.models.user.Address;
import org.api.models.user.Geolocation;
import org.api.models.user.Name;
import org.api.models.user.User;
import org.api.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.api.utils.Utils.mapResponseToObject;
import static org.api.utils.Utils.mapResponseToObjectsList;

public class UserService implements IUserInterface {
    RestClient client=new RestClient();
    HttpRequest request;
    ObjectMapper mapper=new ObjectMapper();

    /**
     * Retrieves a list of all users.
     * @return List<user> A list of all users.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public List<User> getAllUsers() throws RestClient.HttpRequestException, IOException {
        try{
            request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL + Constants.USERS_ENDPOINT,null,null);
            return mapResponseToObjectsList(client.executeRequest(request), User.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while fetching all users: "+ e.getMessage());
            throw e;
        }

    }
    /**
     * Retrieves a user by its ID.
     * @param Id The ID of the user.
     * @return user The user with the specified ID.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public User getUserById(int Id) throws RestClient.HttpRequestException, IOException {

        try{
            request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL + Constants.USERS_ENDPOINT+Id,null,null);
            return mapResponseToObject(client.executeRequest(request), User.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while fetching user: "+Id+ ""+ e.getMessage());
            throw e;
        }
    }
    /**
     * Adds a new user.
     * @param user The user to be added.
     * @return user The added user.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public User addUser(User user) throws IOException, RestClient.HttpRequestException {
        try{
            request=new HttpRequest(HttpMethod.POST, Constants.BASE_URL + Constants.USERS_ENDPOINT, mapper.writeValueAsString(user),Utils.createJsonHeader());
            return mapResponseToObject(client.executeRequest(request), User.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while adding user: "+ e.getMessage());
            throw e;
        }
    }
    /**
     * Updates an existing user.
     * @param user The user with updated information.
     * @return user The updated user.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public User updateUser(User user) throws RestClient.HttpRequestException, IOException {
        try {
            request = new HttpRequest(HttpMethod.PUT, Constants.BASE_URL + Constants.USERS_ENDPOINT + user.getId(), mapper.writeValueAsString(user), Utils.createJsonHeader());
            return mapResponseToObject(client.executeRequest(request), User.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while updating user: "+ e.getMessage());
            throw e;
        }
    }
    /**
     * Deletes a user by its ID.
     * @param Id The ID of the user to be deleted.
     * @return user The deleted user.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     **/
    @Override
    public User deleteUser(int Id) throws RestClient.HttpRequestException, IOException {
        try {
            request = new HttpRequest(HttpMethod.DELETE, Constants.BASE_URL + Constants.USERS_ENDPOINT + Id, null, Utils.createJsonHeader());
            return mapResponseToObject(client.executeRequest(request), User.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while deleting user: "+Id+ ""+ e.getMessage());
            throw e;
        }
    }

}
