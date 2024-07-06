package org.api.interfaces;

import okhttp3.Response;
import org.api.clients.RestClient;
import org.api.models.Product;

import java.io.IOException;
import java.util.List;

public interface IProductInterface {

    /**
     *
     * @return  List of all products
     */
    public List<Product> getAllProducts() throws Exception;

    /**
     *
     * @return  product details (Id,title,description,image,price,category,rating)
     * @throws IOException
     */
    public Product getProductById(int Id) throws IOException, RestClient.HttpRequestException;

    /**
     *
     * @param product
     * @return
     * @throws IOException
     */
    /**
     *
     * @param product : product Json object
     * @return : product details
     * @throws IOException
     */
    public Product addProduct(Product product) throws IOException, RestClient.HttpRequestException;

    /**
     *
     * @param product : Json object
     * @return : product details
     * @throws IOException
     */
    public Product updateProduct(Product product) throws IOException, RestClient.HttpRequestException;

    /**
     *
     * @param id: product Id
     * @return : product details
     * @throws IOException
     */
    public Response deleteProduct(int id) throws IOException, RestClient.HttpRequestException;

    public List<String> getAllCategories() throws IOException, RestClient.HttpRequestException;
    public List<Product> getAllProductsInACategory(String category) throws IOException, RestClient.HttpRequestException;
}
