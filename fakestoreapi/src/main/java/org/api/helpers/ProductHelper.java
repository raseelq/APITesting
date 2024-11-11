package org.api.helpers;

import org.api.models.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductHelper {
    /**
     * Maps a list of lists of strings from a data file to a list of Product objects.
     * @param records The list of lists of strings to be mapped.
     * @return List<Product> A list of Product objects.
     * @throws Exception If the number of fields in the data file is incorrect.
     */
    public static List<Product> mapProductsListFromDataFile(List<List<String>> records) throws Exception {
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
