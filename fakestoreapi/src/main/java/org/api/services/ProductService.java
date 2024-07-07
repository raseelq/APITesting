package org.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.api.clients.RestClient;
import org.api.constants.Constants;
import org.api.interfaces.IProductInterface;
import org.api.models.HttpRequest;
import org.api.models.Product;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.api.utils.Utils.createHeader;

public class ProductService implements IProductInterface {
    RestClient client=new RestClient();
    ObjectMapper mapper=new ObjectMapper();
    @Override
    public List<Product> getAllProducts() throws RestClient.HttpRequestException, IOException {
        HttpRequest request=new HttpRequest("get", Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT,null, null);
        return mapper.readValue(client.executeRequest(request).body().string(),new TypeReference<List<Product>>() {});
    }

    @Override
    public Product getProductById(int Id) throws IOException, RestClient.HttpRequestException {
        HttpRequest request=new HttpRequest("get", Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Id,null, null);
        String body=client.executeRequest(request).body().string();
        if (body != null && !body.isEmpty()) {
            return mapper.readValue(body, Product.class);
        }
        else{
            return null;
        }

    }

    @Override
    public Product addProduct(Product product) throws IOException, RestClient.HttpRequestException {
        Map<String,String> header=createHeader("content-type","application/json; charset=UTF-8");
        HttpRequest request=new HttpRequest("POST", Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT, mapper.writeValueAsString(product), header);
        return mapper.readValue(client.executeRequest(request).body().string(),Product.class);
    }

    @Override
    public Product updateProduct(Product product) throws IOException, RestClient.HttpRequestException {
        Map<String,String> header=createHeader("content-type","application/json; charset=UTF-8");
        HttpRequest request=new HttpRequest("PUT", Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+product.getId(), mapper.writeValueAsString(product), header);
        return mapper.readValue(client.executeRequest(request).body().string(),Product.class);

    }

    @Override
    public Response deleteProduct(int Id) throws IOException, RestClient.HttpRequestException {
        HttpRequest request=new HttpRequest("DELETE", Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Id,null, null);
        return client.executeRequest(request);
    }

    @Override
    public List<String> getAllCategories() throws IOException, RestClient.HttpRequestException {
        HttpRequest request=new HttpRequest("get", Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Constants.GET_ALL_CATEGORIES_ENDPOINT,null, null);
        return mapper.readValue(client.executeRequest(request).body().string(),new TypeReference<List<String>>() {});

    }

    @Override
    public List<Product> getAllProductsInACategory(String category) throws IOException, RestClient.HttpRequestException {
        HttpRequest request=new HttpRequest("get", Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT+Constants.GET_CATEGORY_PRODUCTS_ENDPOINT+category,null, null);
        return mapper.readValue(client.executeRequest(request).body().string(),new TypeReference<List<Product>>() {});
    }

    @Override
    public List<Product> sortAllProductsDescending() throws IOException, RestClient.HttpRequestException {
        HttpRequest request=new HttpRequest("get", Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT_SORT_LIMIT+Constants.SORT_DESC,null, null);
        return mapper.readValue(client.executeRequest(request).body().string(),new TypeReference<List<Product>>() {});

    }

    @Override
    public List<Product> limitProductsResults(int limit) throws RestClient.HttpRequestException, IOException {
        HttpRequest request=new HttpRequest("Get",Constants.BASE_URL+Constants.GET_ALL_PRODUCTS_ENDPOINT_SORT_LIMIT+Constants.LIMIT+limit,null,null);

        return mapper.readValue(client.executeRequest(request).body().string(), new TypeReference<List<Product>>() {});

    }


}
