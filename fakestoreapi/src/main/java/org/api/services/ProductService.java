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
        request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT,null, null);
        return mapResponseToObjectsList(client.executeRequest(request), Product.class);
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
        HttpRequest request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Id,null, null);
        return mapResponseToObject(client.executeRequest(request), Product.class);
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
        Map<String,String> header= Utils.createJsonHeader();
        HttpRequest request=new HttpRequest(HttpMethod.POST, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT, mapper.writeValueAsString(product), header);
        return mapResponseToObject(client.executeRequest(request), Product.class);
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
        Map<String,String> header= Utils.createJsonHeader();
        HttpRequest request=new HttpRequest(HttpMethod.PUT, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+product.getId(), mapper.writeValueAsString(product), header);
        return mapResponseToObject(client.executeRequest(request), Product.class);
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
        HttpRequest request=new HttpRequest(HttpMethod.DELETE, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Id,null, null);
        return mapResponseToObject(client.executeRequest(request), Product.class);
    }
    /**
     * Retrieves all product categories.
     * @return List<String> A list of product categories.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public List<String> getAllCategories() throws IOException, RestClient.HttpRequestException {
        HttpRequest request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Constants.GET_ALL_CATEGORIES_ENDPOINT,null, null);
        return mapResponseToObjectsList(client.executeRequest(request), String.class);
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
        HttpRequest request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Constants.GET_CATEGORY_PRODUCTS_ENDPOINT+category,null, null);
        return mapResponseToObjectsList(client.executeRequest(request), Product.class);
    }
    /**
     * Retrieves all products sorted in descending order.
     * @return List<Product> A list of products sorted in descending order.
     * @throws IOException If there is an I/O error.
     * @throws RestClient.HttpRequestException If there is an error in the HTTP request.
     */
    @Override
    public List<Product> sortAllProductsDescending() throws IOException, RestClient.HttpRequestException {
        HttpRequest request=new HttpRequest(HttpMethod.GET, Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT_SORT_LIMIT+Constants.SORT_DESC,null, null);
        return mapResponseToObjectsList(client.executeRequest(request), Product.class);
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
        HttpRequest request=new HttpRequest(HttpMethod.GET,Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT_SORT_LIMIT+Constants.LIMIT+limit,null,null);
        return mapResponseToObjectsList(client.executeRequest(request), Product.class);
    }
    /**
     * Maps a list of lists of strings from a data file to a list of Product objects.
     * @param records The list of lists of strings to be mapped.
     * @return List<Product> A list of Product objects.
     * @throws Exception If the number of fields in the data file is incorrect.
     */
    public List<Product> mapProductsListFromDataFile(List<List<String>> records) throws Exception {
        List<Product> products=new ArrayList<>();
        int i=1;
        //find number of fields(column names
        if(records==null || records.size()<=1){
            throw new Exception("Data file is empty or contains only headers");
        }
        try{
            int fieldsCount=records.get(0).size();
            for(i=1;i<records.size();i++){
                //Verify that the number of fields(represented by headers) in the data file is correct before mapping
                if(records.get(i).size()!=fieldsCount) {
                    throw new Exception("Data file fields count is not correct");
                }
                    Product product = new Product();
                    product.setId(Integer.parseInt(records.get(i).get(0)));
                    product.setTitle(records.get(i).get(1));
                    product.setPrice(Double.parseDouble(records.get(i).get(2)));
                    product.setCategory(records.get(i).get(3));
                    product.setDescription(records.get(i).get(4));
                    product.setImage(records.get(i).get(5));
                    products.add(product);
                }

        }catch(NumberFormatException e){
            throw new Exception("Invalid format at record index "+ i + ":"+ e.getMessage());
        }catch (ArrayIndexOutOfBoundsException e){
            throw new Exception("Array index " + i + "is out of access "+ ":"+ e.getMessage());
        }
        return products;
    }




}
