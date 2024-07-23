package org.api.interfaces;

import org.api.clients.RestClient;
import org.api.models.user.User;

import java.io.IOException;
import java.util.List;

public interface IUserInterface {

    public List<User> getAllUsers() throws RestClient.HttpRequestException, IOException;
    public User getUserById(int Id) throws RestClient.HttpRequestException, IOException;
    public User addUser(User user) throws RestClient.HttpRequestException, IOException;;
    public User updateUser(User user) throws RestClient.HttpRequestException, IOException;
    public User deleteUser(int id) throws RestClient.HttpRequestException, IOException;
}
