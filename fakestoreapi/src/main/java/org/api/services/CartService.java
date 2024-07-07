package org.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.api.clients.RestClient;
import org.api.constants.Constants;
import org.api.interfaces.ICartInterface;
import org.api.models.Cart;
import org.api.models.HttpRequest;
import org.api.models.Product;
import org.api.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CartService implements ICartInterface {
    HttpRequest request;
    RestClient client=new RestClient();
    ObjectMapper mapper=new ObjectMapper();

    @Override
    public List<Cart> getAllCarts() throws RestClient.HttpRequestException, IOException {
        request=new HttpRequest("get", Constants.BASE_URL+Constants.GET_ALL_CARTS_ENDPOINT,null,null);
        String body=client.executeRequest(request).body().string();
        return  mapper.readValue(Objects.requireNonNull(client.executeRequest(request).body()).string(), new TypeReference<List<Cart>>() {
        });
    }

    @Override
    public Cart getCartById(int Id) throws RestClient.HttpRequestException, IOException {
        request=new HttpRequest("get", Constants.BASE_URL+Constants.GET_ALL_CARTS_ENDPOINT+Id,null,null);
        return  mapper.readValue(client.executeRequest(request).body().string(), Cart.class);

    }

    @Override
    public Cart addNewCart(Cart cart) throws RestClient.HttpRequestException, IOException {
        Map<String,String> jsonHeader= Utils.createJsonHeader();
        request=new HttpRequest("POST", Constants.BASE_URL+Constants.GET_ALL_CARTS_ENDPOINT,mapper.writeValueAsString(cart),jsonHeader);
        return  mapper.readValue(client.executeRequest(request).body().string(), Cart.class);

    }

    @Override
    public Cart updateCart(Cart cart) throws RestClient.HttpRequestException, IOException {
        Map<String,String> jsonHeader= Utils.createJsonHeader();
        request=new HttpRequest("PUT", Constants.BASE_URL+Constants.GET_ALL_CARTS_ENDPOINT,mapper.writeValueAsString(cart),jsonHeader);
        return  mapper.readValue(client.executeRequest(request).body().string(), Cart.class);
    }


    @Override
    public Cart deleteCart(int Id) throws RestClient.HttpRequestException, IOException {
        HttpRequest request=new HttpRequest("DELETE", Constants.BASE_URL+Constants.GET_ALL_CARTS_ENDPOINT+Id,null, null);
        return mapper.readValue(client.executeRequest(request).body().string(), Cart.class);

    }

    @Override
    public List<Cart> getUserCart(int userId) throws RestClient.HttpRequestException, IOException {
        request = new HttpRequest("get", Constants.BASE_URL + Constants.GET_ALL_CARTS_ENDPOINT + Constants.GET_USER_ENDPOINT + userId, null, null);
        String body = client.executeRequest(request).body().string();
        return mapper.readValue(client.executeRequest(request).body().string(), new TypeReference<List<Cart>>() {
        });
    }

    @Override
    public List<Cart> getCartInDateRange(String startDate, String endDate) throws RestClient.HttpRequestException, IOException {
        request = new HttpRequest("get", Constants.BASE_URL + Constants.CARTS_ENDPOINT + "startdate="+ startDate +"&"+"enddate="+endDate, null, null);
        String body = client.executeRequest(request).body().string();
        return mapper.readValue(client.executeRequest(request).body().string(), new TypeReference<List<Cart>>() {
        });
    }

    @Override
    public List<Cart> sortAllCartsDescending() throws IOException, RestClient.HttpRequestException {
        HttpRequest request=new HttpRequest("get", Constants.BASE_URL+Constants.CARTS_ENDPOINT+"sort=desc",null, null);
        return mapper.readValue(client.executeRequest(request).body().string(),new TypeReference<List<Cart>>() {});
    }

    @Override
    public List<Cart> limitCartsResults(int limit) throws IOException, RestClient.HttpRequestException {
        HttpRequest request=new HttpRequest("get", Constants.BASE_URL+Constants.CARTS_ENDPOINT+"limit="+limit,null, null);
        return mapper.readValue(client.executeRequest(request).body().string(),new TypeReference<List<Cart>>() {});
    }
}
