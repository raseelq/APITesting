package org.api.interfaces;

import org.api.clients.RestClient;
import org.api.models.cart.Cart;
import java.io.IOException;
import java.util.List;

public interface ICartInterface {

    public List<Cart> getAllCarts() throws RestClient.HttpRequestException, IOException;
    public Cart getCartById(int Id) throws RestClient.HttpRequestException, IOException;
    public Cart addNewCart(Cart cart) throws RestClient.HttpRequestException, IOException;
    public Cart updateCart(Cart cart) throws RestClient.HttpRequestException, IOException;
    public Cart deleteCart(int Id) throws RestClient.HttpRequestException, IOException;
    public List<Cart> getUserCart(int userId) throws RestClient.HttpRequestException, IOException;
    public List<Cart> getCartInDateRange(String date1,String date2) throws RestClient.HttpRequestException, IOException;
    public List<Cart> sortAllCarts(String sort) throws IOException, RestClient.HttpRequestException;
    public List<Cart> limitCartsResults(int limit) throws IOException, RestClient.HttpRequestException;


}
