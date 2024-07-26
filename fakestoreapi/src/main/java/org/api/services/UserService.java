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
    /**
     * Maps a list of lists of strings to a list of user objects.
     * @param records The list of lists of strings to be mapped read from csv file.
     * @return List<user> A list of user objects.
     */
    public List<User> mapListToUserObject(List<List<String>> records) throws Exception {
        ArrayList<User> users=new ArrayList<>();
        int i=1;
        if(records==null || records.size()<=1){
            throw new Exception("Data file is empty or contains only headers");
        }
        try {
            for (i = 1; i < records.size(); i++) {
                User user = new User();
                List<String> record = records.get(i);
                user.setId(Integer.parseInt(record.get(0)));
                user.setEmail(record.get(1));
                user.setUsername(record.get(2));
                user.setPassword(record.get(3));

                Name name = new Name();
                name.setFirstname(record.get(4));
                name.setLastname(record.get(5));
                user.setName(name);

                Address address = new Address();
                address.setCity(record.get(6));
                address.setStreet(record.get(7));
                address.setNumber(Integer.parseInt(record.get(8)));
                address.setZipcode(record.get(9));
                user.setAddress(address);


                Geolocation geolocation = new Geolocation();
                geolocation.setLat(record.get(10));
                geolocation.setLongi(record.get(11));
                address.setGeolocation(geolocation);
                user.setPhone(record.get(12));
                users.add(user);
            }
        }
        catch(NumberFormatException e){
            throw new Exception("Invalid format at record index "+ i + ":"+ e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new Exception("Array index " + i + "is out of access "+ ":"+ e.getMessage());
        }
        return users;

    }
    /**
     * Returns a specific user from Users list by its index.
     * @param users The list of users
     * @param index index of user
     * @return User object.
     */
    @Override
    public User getUserFromListByIndex(List<User> users,int index){
        return users.get(index);
    }
    /**
     * Maps a list of lists of strings from a data file to a list of User objects.
     * @param records The list of lists of strings to be mapped.
     * @return List<Product> A list of User objects.
     * @throws Exception If the number of fields in the data file is incorrect.
     */
    @Override
    public List<User> mapListToUsers(List<List<String>> records) {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < records.size(); i++) {
            User user = new User();
            user.setId(Integer.parseInt(records.get(i).get(0)));
            user.setEmail(records.get(i).get(1));
            user.setUsername(records.get(i).get(2));
            user.setPassword(records.get(i).get(3));
            Name name = new Name(records.get(i).get(4), records.get(i).get(5));
            user.setName(name);
            Geolocation geolocation = new Geolocation(records.get(i).get(10), records.get(i).get(11));
            Address address = new Address(records.get(i).get(6),
                    records.get(i).get(7),
                    Integer.parseInt(records.get(i).get(8)),
                    records.get(i).get(9), geolocation);
            user.setAddress(address);

            users.add(user);

        }
        return users;
    }
}
