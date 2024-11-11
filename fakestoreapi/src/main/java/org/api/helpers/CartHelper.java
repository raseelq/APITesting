package org.api.helpers;

import org.api.models.cart.Cart;
import org.api.models.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartHelper {

    /**
     * Maps a list of lists of strings to a list of Cart objects.
     * @param records The list of lists of strings to be mapped.
     * @return List<Cart> A list of Cart objects.
     */
    public static List<Cart> mapListToCarts(List<List<String>> records) {
        List<Cart> carts=new ArrayList<>();
        for(int i=1;i<records.size();i++){
            Cart cart=new Cart();
            cart.setId(Integer.parseInt(records.get(i).get(0)));
            cart.setUserId(Integer.parseInt(records.get(i).get(1)));
            cart.setDate(records.get(i).get(2));
            List<CartItem> items=new ArrayList<>();
            for(int j=3;j<records.get(i).size();j+=2){
                CartItem item=new CartItem();
                item.setProductId(Integer.parseInt(records.get(i).get(j)));
                item.setQuantity(Integer.parseInt(records.get(i).get(j+1)));
                items.add(item);
            }
            cart.setProducts(items);
            carts.add(cart);

        }
        return carts;

    }
}
