package carts;

import com.beust.ah.A;
import org.api.clients.RestClient;
import org.api.constants.Constants;
import org.api.interfaces.ICartInterface;
import org.api.interfaces.IProductInterface;
import org.api.models.cart.Cart;
import org.api.models.cart.CartItem;
import org.api.models.product.Product;
import org.api.services.CartService;
import org.api.reports.ExtentReportsListeners;
import org.api.services.ProductService;
import org.api.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


@Listeners(ExtentReportsListeners.class)
public class CartsPositiveScenariosTests extends CartsBaseClass{

    @Test
    private void verifyGetAllCarts() throws RestClient.HttpRequestException, IOException {
        //verify the number of carts returned by get all carts API matches the number of carts in data file
        List<Cart> cartsList=cartService.getAllCarts();
        for(int i=0;i<cartsList.size();i++){
            Assert.assertTrue(cartsList.get(i).equals(carts.get(i)));
        }
        Assert.assertEquals(cartsList, carts, "The returned carts list does not match the expected list");

    }
    @Test(dataProvider ="cartsIdsProvider")
    private void verifyGetCartById(int Id) throws RestClient.HttpRequestException, IOException{
        //get cart by Id and verify that it is equivalent to the cart that has the same Id in the csv file
        Cart cart=cartService.getCartById(Id);
        Assert.assertTrue(cart.equals(carts.get(Id-1)),"The cart fetched by ID does not match the expected cart");
    }
    @Test
    private void verifyAddNonExistingCart() throws RestClient.HttpRequestException, IOException {
        Cart newCart= cartService.addNewCart(cartsTestData.get("validCartTestPayload"));
        Cart expectedCart = cartsTestData.get("validCartTestPayload");
        Assert.assertEquals(newCart.getUserId(),expectedCart.getUserId());
        Assert.assertEquals(newCart.getDate(),expectedCart.getDate());
        Assert.assertTrue(newCart.getProducts().equals(expectedCart.getProducts()));
        // Uncomment these two lines if the API increments the size of the cart list properly
//        int beforeAddSize = cartService.getAllCarts().size();
//        Assert.assertEquals(cartService.getAllCarts().size(),beforeAddSize+1);
    }
    @Test
    private void verifyUpdateCart() throws RestClient.HttpRequestException, IOException {
        Cart cart=carts.get(0);
        //generate a random existing userId
        int newUserId=Utils.generateRandomInt(1,8);
        cart.setUserId(newUserId);
        cart.setDate("2019-12-10");
        List<CartItem> items = new ArrayList<>();
        items.add(createCartItem(1, 3));
        cart.setProducts(items);
        Cart updatedCart = cartService.updateCart(cart);
        //verify the changed fields are returned in the response with the new values
        Assert.assertEquals(updatedCart.getUserId(),cart.getUserId());
        Assert.assertEquals(updatedCart.getDate(),cart.getDate());
        Assert.assertTrue(updatedCart.getProducts().equals(cart.getProducts()));
        //verify the unchanged fields are still unchanged
        Assert.assertEquals(updatedCart.getId(),cart.getId());
    }

    private CartItem createCartItem(int productId,int quantity){
        return new CartItem(productId,quantity);
    }

    @Test(dataProvider = "cartsIdsProvider")
    private void verifyDeleteCart(int id) throws RestClient.HttpRequestException, IOException {
        int sizeBeforeDelete=carts.size();
        Cart deleted =cartService.deleteCart(id);
        List<Cart> afterDelete=cartService.getAllCarts();
        Assert.assertEquals(afterDelete.size(),sizeBeforeDelete-1,"Cart deletion did not decrease the total cart count as expected"); //Fails because delete carts API doesn't work properly
    }
    @Test(dataProvider = "cartsIdsProvider")
    private void verifyGetUserCart(int userId) throws RestClient.HttpRequestException, IOException {
        List<Cart> userCarts=cartService.getUserCart(userId);
        for(Cart cart:userCarts){
            Assert.assertEquals(cart.getUserId(),userId,"Cart userId does not match the expected userId");
        }

    }
    @Test
    private void verifyGetCartInDateRange() throws RestClient.HttpRequestException, IOException {
        String startDate="2019-12-10";
        String endDate="2020-10-10";
        LocalDate formattedStartDate=Utils.generateRandomDate(startDate,endDate);
        LocalDate formattedEndDate=Utils.generateRandomDate(formattedStartDate.toString(),endDate);
        List<Cart> carts=cartService.getCartInDateRange(formattedStartDate.toString(),formattedEndDate.toString());
        for(Cart cart:carts){
            LocalDate cartDate=Utils.dateFormatter(cart.getDate());
            Assert.assertTrue(cartDate.isAfter(formattedStartDate) && cartDate.isBefore(formattedEndDate),
                    "Cart date " + cartDate + " is not within the expected date range " + formattedStartDate + " to " + formattedEndDate);
        }
    }
    @Test
    public void verifyProductLimits() throws RestClient.HttpRequestException, IOException {
        int limit=Utils.generateRandomInt(2,carts.size());
        List<Cart> limitCartsResult=cartService.limitCartsResults(limit);
        Assert.assertEquals(limitCartsResult.size(),limit,"The number of carts returned does not match the specified limit");
    }
    @Test
    public void verifyCartsAreSortedDesc() throws RestClient.HttpRequestException, IOException {
        List<Cart> carts=cartService.sortAllCarts("desc");
        for(int i=0;i<carts.size()-1;i++){
            Assert.assertTrue(carts.get(i).getId() > carts.get(i+1).getId(),"Carts are not sorted in descending order by ID");
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
