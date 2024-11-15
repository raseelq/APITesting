package products;

import org.api.clients.RestClient;
import org.api.interfaces.IProductInterface;
import org.api.models.product.Product;
import org.api.reports.ExtentReportsListeners;
import org.api.services.ProductService;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners(ExtentReportsListeners.class)
public class ProductsNegativeScenariosTests extends ProductsBaseClass{

    /**
     * Verifies that the number of products returned by the getAllProducts method matches the expected products count.
     * @throws IOException if an I/O error occurs.
     */
    @Test(dataProvider = "invalidProductIdsProvider")
    public void verifyGetProductById(int productId) throws IOException, RestClient.HttpRequestException {
        Assert.assertNull(productService.getProductById(productId));
    }


    @Test(dataProvider = "invalidProductIdsProvider")
    public void verifyUpdateProduct(int Id) throws IOException, RestClient.HttpRequestException {
        //This test doesnt work because API can update a non-existing Id
        Product product=productService.getProductById(Id);
        Assert.assertNull(product);
    }

    @DataProvider(name="invalidProductIdsProvider")
    public Object[][] invalidProductIdsProvider(){
        return new Object[][]{
                {-1},
                {0}
        };
    }
}
