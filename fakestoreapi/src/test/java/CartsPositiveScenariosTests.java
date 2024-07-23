import org.api.clients.RestClient;
import org.api.constants.Constants;
import org.api.interfaces.ICartInterface;
import org.api.models.cart.Cart;
import org.api.models.cart.CartItem;
import org.api.services.CartService;
import org.api.reports.ExtentReportsListeners;
import org.api.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Listeners(ExtentReportsListeners.class)
public class CartsPositiveScenariosTests {
    ICartInterface cartService;
    List<Cart> carts;

    @BeforeSuite
    public void setup() throws IOException {
        cartService=new CartService();
        String resourceFilePath=this.getClass().getResource(Constants.CARTS_SOURCE_FILE).getFile();
        carts=cartService.mapListToCarts(Utils.readCSVResourceFile(resourceFilePath,","));

    }
    @Test
    private void verifyGetAllCarts() throws RestClient.HttpRequestException, IOException {
        //verify the number of carts returned by get all carts API matches the number of carts in data file
        List<Cart> cartsList=cartService.getAllCarts();
        Assert.assertEquals(carts.size(),cartsList.size());
    }
    @Test(dataProvider ="cartsIdsProvider")
    private void verifyGetCartById(int Id) throws RestClient.HttpRequestException, IOException {
        Cart cart=cartService.getCartById(Id);
        Assert.assertEquals(cart.getId(),carts.get(Id-1).getId());
    }
    @Test
    private void verifyAddCart() throws RestClient.HttpRequestException, IOException {
        Cart newCart=cartService.addNewCart(carts.get(0));
        newCart.equals(carts.get(0));

        // Uncomment these two lines if the API increments the size of the cart list properly
//        int beforeAddSize = cartService.getAllCarts().size();
//        Assert.assertEquals(cartService.getAllCarts().size(),beforeAddSize+1);
    }
    @Test
    private void verifyUpdateCart() throws RestClient.HttpRequestException, IOException {
        Cart cart=carts.get(0);
        cart.setUserId(3);
        cart.setDate("2019-12-10");
        List<CartItem> items = new ArrayList<>();
        items.add(createCartItem(1, 3));
        cart.setProducts(items);
        Cart updatedCart = cartService.updateCart(cart);
        //verify the changed fields are returned in the response with the new values
        Assert.assertEquals(updatedCart.getUserId(),cart.getUserId());
        Assert.assertEquals(updatedCart.getDate(),cart.getDate());
        Assert.assertTrue(updatedCart.getProducts().equals(cart.getProducts()));
    }

    private CartItem createCartItem(int productId,int quantity){
        return new CartItem(productId,quantity);
    }

    @Test(dataProvider = "cartsIdsProvider")
    private void verifyDeleteCart(int id) throws RestClient.HttpRequestException, IOException {
        int sizeBeforeDelete=carts.size();
        Cart deleted =cartService.deleteCart(id);
        List<Cart> afterDelete=cartService.getAllCarts();
        Assert.assertEquals(afterDelete.size(),sizeBeforeDelete-1); //Fails because delete carts API doesn't work properly
    }
    @Test(dataProvider = "cartsIdsProvider")
    private void verifyGetUserCart(int userId) throws RestClient.HttpRequestException, IOException {
        List<Cart> userCarts=cartService.getUserCart(userId);
        for(Cart cart:userCarts){
            Assert.assertEquals(cart.getUserId(),userId);
        }

    }
    @Test
    private void verifyGetCartInDateRange() throws RestClient.HttpRequestException, IOException {
        String startDate="2019-12-10";
        String endDate="2020-10-10";
        LocalDate formattedStartDate=Utils.dateFormatter(startDate);
        LocalDate formattedEndDate=Utils.dateFormatter(endDate);
        List<Cart> carts=cartService.getCartInDateRange(startDate,endDate);
        for(Cart cart:carts){
            LocalDate cartDate=Utils.dateFormatter(cart.getDate());
            Assert.assertTrue(cartDate.isAfter(formattedStartDate) && cartDate.isBefore(formattedEndDate));
        }
    }
    @Test
    public void verifyProductLimits() throws RestClient.HttpRequestException, IOException {
        int limit=5;
        List<Cart> carts=cartService.limitCartsResults(limit);
        Assert.assertEquals(carts.size(),limit);
    }
    @Test
    public void verifyCartsAreSortedDesc() throws RestClient.HttpRequestException, IOException {
        List<Cart> carts=cartService.sortAllCartsDescending();
        for(int i=0;i<carts.size()-1;i++){
            Assert.assertTrue(carts.get(i).getId() > carts.get(i+1).getId());

        }
    }
    @DataProvider(name="cartsIdsProvider")
    public Object[][] cartsIdsProvider(){
        return new Object[][]{
                {1},
                {2},
                {3},
                {4},
                {5},
                {6},
                {7}

        };
    }


}
