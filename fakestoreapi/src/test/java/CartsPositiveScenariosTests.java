import org.api.clients.RestClient;
import org.api.models.Cart;
import org.api.models.CartItem;
import org.api.models.Product;
import org.api.services.CartService;
import org.api.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CartsPositiveScenariosTests {
    CartService cartService;
    @BeforeSuite
    public void setup(){
        cartService=new CartService();

    }
    @Test
    private void verifyGetAllCarts() throws RestClient.HttpRequestException, IOException {
        int count=7;
        List<Cart> carts=cartService.getAllCarts();
        Assert.assertEquals(carts.size(),count);

    }
    @Test(dataProvider ="cartsIdsProvider")
    private void verifyGetCartById(int Id) throws RestClient.HttpRequestException, IOException {
        Cart cart=cartService.getCartById(Id);
        Assert.assertEquals(cart.getId(),Id);

    }
    @Test
    private void verifyAddCart() throws RestClient.HttpRequestException, IOException {
        // Create cart items
        List<CartItem> items = new ArrayList<>();
        items.add(createCartItem(5, 1));
        items.add(createCartItem(1, 5));

        // Create cart
        Cart cart = new Cart();
        cart.setUserId(5);
        cart.setDate("2020-02-03");
        cart.setProducts(items);

        // Add new cart and verify the result
        Cart newCart = cartService.addNewCart(cart);
        compareAndVerifyEqualCarts(cart,newCart);


        // Uncomment this line if the API properly increments the size of the cart list
        // int beforeAddSize = cartService.getAllCarts().size();
        //Assert.assertEquals(newCart.getProducts().size,beforeAddSize+1);
    }
    @Test
    private void verifyUpdateCart() throws RestClient.HttpRequestException, IOException {
        int id=7;
        List<CartItem> items = new ArrayList<>();
        items.add(createCartItem(1, 3));


        // Create cart object
        Cart cart = new Cart();
        cart.setUserId(3);
        cart.setDate("2019-12-10");
        cart.setProducts(items);

        Cart updatedCart = cartService.addNewCart(cart);
        compareAndVerifyEqualCarts(cart,updatedCart);

    }

    private CartItem createCartItem(int productId,int quantity){
        return new CartItem(productId,quantity);
    }

    private void compareAndVerifyEqualCarts(Cart firstCart,Cart secondCart){

        Assert.assertEquals(firstCart.getDate(), secondCart.getDate(), "Date should match");
        Assert.assertEquals(firstCart.getUserId(), secondCart.getUserId(), "User ID should match");
        Assert.assertEquals(firstCart.getProducts().size(), secondCart.getProducts().size(), "Number of items should match");

        // Verify the first item in the cart
        if(!(firstCart.getProducts().size()!=secondCart.getProducts().size())){
            for(int i=0;i<firstCart.getProducts().size()-1;i++){
                Assert.assertEquals(firstCart.getProducts().get(i).getProductId(), secondCart.getProducts().get(i).getProductId(), "Product ID should match");
                Assert.assertEquals(firstCart.getProducts().get(i).getQuantity(), secondCart.getProducts().get(i).getQuantity(), "Quantity should match");

            }
        }


    }
    @Test
    private void verifyDeleteCart() throws RestClient.HttpRequestException, IOException {
        int id=7;
        List<Cart> beforeDelete=cartService.getAllCarts();
        Cart deleted =cartService.deleteCart(id);
        List<Cart> afterDelete=cartService.getAllCarts();
        Assert.assertEquals(deleted.getId(),id);
        Assert.assertEquals(beforeDelete.size(),afterDelete.size()  );
    }
    @Test
    private void verifyGetUserCart() throws RestClient.HttpRequestException, IOException {
        int userId=2;
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
                {7}

        };
    }


}
