package org.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.api.clients.RestClient;
import org.api.constants.Constants;
import org.api.interfaces.IProductInterface;
import org.api.models.api.HttpMethod;
import org.api.models.api.HttpRequest;
import org.api.models.product.Product;
import org.api.utils.Utils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.api.utils.Utils.*;

public class ProductService implements IProductInterface {
    RestClient client=new RestClient();
    ObjectMapper mapper=new ObjectMapper();
    HttpRequest request;

    /**
     * Retrieves a list of all products.
     * @return List<Product> A list of all products.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public List<Product> getAllProducts() throws RestClient.HttpRequestException, IOException {
        try{
            request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT,null, null);
            return mapResponseToObjectsList(client.executeRequest(request), Product.class);
        }catch (RestClient.HttpRequestException | IOException e ){
            System.err.println("Error occurred while fetching all products: "+ e.getMessage());
            throw e;
        }

    }
    /**
     * Retrieves a product by its ID.
     * @param Id The ID of the product.
     * @return Product The product with the specified ID.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public Product getProductById(int Id) throws IOException, RestClient.HttpRequestException {
        try {
            HttpRequest request = new HttpRequest(HttpMethod.GET, Constants.BASE_URL + Constants.GET_ALL_PRODUCTS_ENDPOINT + Id, null, null);
            return mapResponseToObject(client.executeRequest(request), Product.class);
        }catch (IOException | RestClient.HttpRequestException e){
            System.err.println("Error occurred while fetching product Id: "+ Id + e.getMessage());
            throw e;

        }
    }
    /**
     * Adds a new product.
     * @param product The product to be added.
     * @return Product The added product.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public Product addProduct(Product product) throws IOException, RestClient.HttpRequestException {
        try{
            Map<String,String> header= Utils.createJsonHeader();
            HttpRequest request=new HttpRequest(HttpMethod.POST, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT, mapper.writeValueAsString(product), header);
            return mapResponseToObject(client.executeRequest(request), Product.class);
        }catch (IOException | RestClient.HttpRequestException e){
            System.err.println("Error occurred while creating product " + e.getMessage());
            throw e;

        }

    }
    /**
     * Updates an existing product.
     * @param product The product with updated information.
     * @return Product The updated product.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public Product updateProduct(Product product) throws IOException, RestClient.HttpRequestException {
        try{
            Map<String,String> header= Utils.createJsonHeader();
            HttpRequest request=new HttpRequest(HttpMethod.PUT, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+product.getId(), mapper.writeValueAsString(product), header);
            return mapResponseToObject(client.executeRequest(request), Product.class);
        }catch (IOException | RestClient.HttpRequestException e){
            System.err.println("Error occurred while updating product " + e.getMessage());
            throw e;
        }

    }
    /**
     * Deletes a product by its ID.
     * @param Id The ID of the product to be deleted.
     * @return Product The deleted product.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public Product deleteProduct(int Id) throws IOException, RestClient.HttpRequestException {
        try{
            HttpRequest request=new HttpRequest(HttpMethod.DELETE, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Id,null, null);
            return mapResponseToObject(client.executeRequest(request), Product.class);
        }catch (IOException | RestClient.HttpRequestException e){
            System.err.println("Error occurred while deleting product Id: " + Id + e.getMessage());
            throw e;
        }

    }
    /**
     * Retrieves all product categories.
     * @return List<String> A list of product categories.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public List<String> getAllCategories() throws IOException, RestClient.HttpRequestException {
        try{
            HttpRequest request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Constants.GET_ALL_CATEGORIES_ENDPOINT,null, null);
            return mapResponseToObjectsList(client.executeRequest(request), String.class);
        }catch (IOException | RestClient.HttpRequestException e){
            System.err.println("Error occurred while fetching all categories "+ e.getMessage());
            throw e;
        }
    }
    /**
     * Retrieves all products in a specific category.
     * @param category The category for which products are to be retrieved.
     * @return List<Product> A list of products in the specified category.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public List<Product> getAllProductsInACategory(String category) throws IOException, RestClient.HttpRequestException {
        try {
            HttpRequest request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Constants.GET_CATEGORY_PRODUCTS_ENDPOINT+category,null, null);
            return mapResponseToObjectsList(client.executeRequest(request), Product.class);
        }catch (IOException | RestClient.HttpRequestException e){
            System.err.println("Error occurred while fetching all products under category: "+ category+ e.getMessage());
            throw e;
        }

    }
    /**
     * Retrieves all products sorted in descending order.
     * @return List<Product> A list of products sorted in descending order.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public List<Product> sortAllProducts(String sort) throws IOException, RestClient.HttpRequestException {
        try {
            HttpRequest request = new HttpRequest(HttpMethod.GET, Constants.BASE_URL + Constants.GET_ALL_PRODUCTS_ENDPOINT_SORT_LIMIT + "sort=" + sort, null, null);
            return mapResponseToObjectsList(client.executeRequest(request), Product.class);
        }catch (IOException | RestClient.HttpRequestException e){
            System.err.println("Error occurred while sorting all products "+ e.getMessage());
            throw e;
        }
    }
    /**
     * Limits the number of products returned in the result set.
     * @param limit The maximum number of products to return.
     * @return List<Product> A list of products limited by the specified number.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public List<Product> limitProductsResults(int limit) throws RestClient.HttpRequestException, IOException {
        try{
            HttpRequest request=new HttpRequest(HttpMethod.GET,Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT_SORT_LIMIT+Constants.LIMIT+limit,null,null);
            return mapResponseToObjectsList(client.executeRequest(request), Product.class);
        }catch (IOException | RestClient.HttpRequestException e){
            System.err.println("Error occurred while limiting results "+ e.getMessage());
            throw e;
        }
    }




}
