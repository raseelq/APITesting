package products;

import org.api.clients.RestClient;
import org.api.constants.Constants;
import org.api.helpers.ProductHelper;
import org.api.interfaces.IProductInterface;
import org.api.models.product.Product;
import org.api.services.ProductService;
import org.api.utils.Utils;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsBaseClass {
    IProductInterface productService;
    List<Product> products;
    Map<String,Product> productsTestData=new HashMap<>();
    List<String> categoriesList;

    @BeforeTest
    protected void setup() throws Exception {
        productService=new ProductService();
        String resourcesFile= this.getClass().getClassLoader().getResource(Constants.PRODUCTS_SOURCE_FILE).getFile();
        List<List<String>> records= Utils.readCSVResourceFile(resourcesFile,",");
        products= ProductHelper.mapProductsListFromDataFile(records);
        productsTestData.put("validTestProductPayload",createValidProductPayload());
    }

    protected Product createValidProductPayload() throws Exception {
        Map<String,Object> payload=new HashMap<>();
        categoriesList=productService.getAllCategories();
        payload.put("title",Utils.generateRandomString(10));
        payload.put("price",Utils.generateRandomDouble());
        payload.put("description","lorem ipsum set");
        payload.put("category",categoriesList.get(Utils.generateRandomInt(0,categoriesList.size()-1)));
        payload.put("image","https://i.pravatar.cc");
        return Utils.mapPayloadToObject(payload,Product.class);
    }
    protected Product createInValidProductPayload() throws Exception {
        Map<String,Object> payload=new HashMap<>();
        categoriesList=productService.getAllCategories();
        payload.put("title",Utils.generateRandomString(10));
        payload.put("price",-1);
        payload.put("description","lorem ipsum set");
        payload.put("category",categoriesList.get(Utils.generateRandomInt(0,categoriesList.size()-1)));
        payload.put("image","https://i.pravatar.cc");
        return Utils.mapPayloadToObject(payload,Product.class);
    }
}
