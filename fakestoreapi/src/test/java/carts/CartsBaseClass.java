package carts;

import org.api.constants.Constants;
import org.api.helpers.CartHelper;
import org.api.interfaces.ICartInterface;
import org.api.interfaces.IProductInterface;
import org.api.models.cart.Cart;
import org.api.models.cart.CartItem;
import org.api.models.product.Product;
import org.api.services.CartService;
import org.api.services.ProductService;
import org.api.utils.Utils;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartsBaseClass {
    ICartInterface cartService=new CartService();
    List<Cart> carts;
    Map<String,Cart> cartsTestData;
    IProductInterface productService=new ProductService();
    List<String> categoriesList;

    @BeforeClass
    protected void setup() throws Exception {
        String resourcesFile= this.getClass().getClassLoader().getResource(Constants.CARTS_SOURCE_FILE).getFile();
        List<List<String>> records= Utils.readCSVResourceFile(resourcesFile,",");
        carts= CartHelper.mapListToCarts(records);
        cartsTestData=new HashMap<>();
        cartsTestData.put("validCartTestPayload",createValidCartPayload());
    }

    protected Cart createValidCartPayload() throws Exception {
        Map<String,Object> payload=new HashMap<>();
        //generate a random existing userId
        payload.put("userId",Utils.generateRandomInt(1,8));
        payload.put("date","2020-02-03");
        CartItem item=new CartItem(Utils.generateRandomInt(0,20),Utils.generateRandomInt(0,10));
        List<CartItem> productsList=new ArrayList<>();
        productsList.add(item);
        payload.put("products",productsList);
        return Utils.mapPayloadToObject(payload,Cart.class);
    }
}
