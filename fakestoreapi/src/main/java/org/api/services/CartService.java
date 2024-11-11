package org.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.clients.RestClient;
import org.api.constants.Constants;
import org.api.interfaces.ICartInterface;
import org.api.models.api.HttpMethod;
import org.api.models.cart.Cart;
import org.api.models.api.HttpRequest;
import org.api.models.cart.CartItem;
import org.api.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.api.utils.Utils.mapResponseToObject;
import static org.api.utils.Utils.mapResponseToObjectsList;

public class CartService implements ICartInterface {
    HttpRequest request;
    RestClient client=new RestClient();
    ObjectMapper mapper=new ObjectMapper();
    /**
     * Retrieves a list of all carts.
     * @return List<Cart> A list of all carts.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public List<Cart> getAllCarts() throws IOException, RestClient.HttpRequestException {
        try{
            request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.GET_ALL_CARTS_ENDPOINT,null,null);
            return mapResponseToObjectsList(client.executeRequest(request), Cart.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while fetching all carts: "+ e.getMessage());
            throw e;
        }
    }
    /**
     * Retrieves a cart by its ID.
     * @param Id The ID of the cart.
     * @return Cart The cart with the specified ID.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public Cart getCartById(int Id) throws IOException, RestClient.HttpRequestException {
        try {
            request = new HttpRequest(HttpMethod.GET, Constants.BASE_URL + Constants.GET_ALL_CARTS_ENDPOINT + Id, null, null);
            return mapResponseToObject(client.executeRequest(request), Cart.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while fetching Cart by Id: "+ Id + "" + e.getMessage());
            throw e;
        }
    }
    /**
     * Adds a new cart.
     * @param cart The cart to be added.
     * @return Cart The added cart.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public Cart addNewCart(Cart cart) throws RestClient.HttpRequestException, IOException {
        try{
            Map<String,String> jsonHeader= Utils.createJsonHeader();
            request=new HttpRequest(HttpMethod.POST, Constants.BASE_URL+Constants.GET_ALL_CARTS_ENDPOINT,mapper.writeValueAsString(cart),jsonHeader);
            return mapResponseToObject(client.executeRequest(request), Cart.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while adding a new cart: "+ e.getMessage());
            throw e;
        }
    }
    /**
     * Updates an existing cart.
     * @param cart The cart with updated information.
     * @return Cart The updated cart.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public Cart updateCart(Cart cart) throws RestClient.HttpRequestException, IOException {
        try{
            Map<String,String> jsonHeader= Utils.createJsonHeader();
            request=new HttpRequest(HttpMethod.PUT, Constants.BASE_URL+Constants.GET_ALL_CARTS_ENDPOINT+cart.getId(),mapper.writeValueAsString(cart),jsonHeader);
            return mapResponseToObject(client.executeRequest(request), Cart.class);
        }
        catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while updating cart: "+ e.getMessage());
            throw e;
        }
    }

    /**
     * Deletes a cart by its ID.
     * @param Id The ID of the cart to be deleted.
     * @return Cart The deleted cart.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     **/
    @Override
    public Cart deleteCart(int Id) throws RestClient.HttpRequestException, IOException {
        try{
            HttpRequest request=new HttpRequest(HttpMethod.DELETE, Constants.BASE_URL+Constants.GET_ALL_CARTS_ENDPOINT+Id,null, null);
            return mapResponseToObject(client.executeRequest(request), Cart.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while deleting cart Id : "+ Id+ ""+ e.getMessage());
            throw e;
        }
    }
    /**
     * Retrieves all carts for a specific user.
     * @param userId The ID of the user.
     * @return List<Cart> A list of carts for the specified user.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public List<Cart> getUserCart(int userId) throws RestClient.HttpRequestException, IOException {
        try{
            request = new HttpRequest(HttpMethod.GET, Constants.BASE_URL + Constants.GET_ALL_CARTS_ENDPOINT + Constants.GET_USER_ENDPOINT + userId, null, null);
            return mapResponseToObjectsList(client.executeRequest(request), Cart.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while fetching user cart: "+ userId+ e.getMessage());
            throw e;
        }
    }
    /**
     * Retrieves carts within a specified date range.
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return List<Cart> A list of carts within the specified date range.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public List<Cart> getCartInDateRange(String startDate, String endDate) throws RestClient.HttpRequestException, IOException {
        try {
            request = new HttpRequest(HttpMethod.GET, Constants.BASE_URL + Constants.CARTS_ENDPOINT + "startdate=" + startDate + "&" + "enddate=" + endDate, null, null);
            return mapResponseToObjectsList(client.executeRequest(request), Cart.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while fetching cart in date range: "+ e.getMessage());
            throw e;
        }
    }
    /**
     * Sorts all carts in descending order.
     * @return List<Cart> A list of carts sorted in descending order.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public List<Cart> sortAllCarts(String sort) throws IOException, RestClient.HttpRequestException {
        try{
            HttpRequest request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.CARTS_ENDPOINT+Constants.SORT+sort,null, null);
            return mapResponseToObjectsList(client.executeRequest(request), Cart.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while sorting all carts: "+ e.getMessage());
            throw e;
        }
    }
    /**
     * Limits the number of carts in the result set.
     * @param limit The maximum number of carts to return.
     * @return List<Cart> A list of carts limited by the specified number.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public List<Cart> limitCartsResults(int limit) throws IOException, RestClient.HttpRequestException {
        try {
            HttpRequest request = new HttpRequest(HttpMethod.GET, Constants.BASE_URL + Constants.CARTS_ENDPOINT + "limit=" + limit, null, null);
            return mapResponseToObjectsList(client.executeRequest(request), Cart.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while limiting sort results: "+ e.getMessage());
            throw e;
        }
    }

}
