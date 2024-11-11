package org.api.helpers;

import org.api.models.user.Address;
import org.api.models.user.Geolocation;
import org.api.models.user.Name;
import org.api.models.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {
    /**
     * Maps a list of lists of strings to a list of user objects.
     * @param records The list of lists of strings to be mapped read from csv file.
     * @return List<user> A list of user objects.
     */
    public static List<User> mapListToUserObject(List<List<String>> records) throws Exception {
        ArrayList<User> users=new ArrayList<>();
        int i=1;
        if(records==null || records.size()<=1){
            throw new Exception("Data file is empty or contains only headers");
        }
        try {
            for (i = 1; i < records.size(); i++) {
                User user = new User();
                List<String> record = records.get(i);
                user.setId(Integer.parseInt(record.get(0)));
                user.setEmail(record.get(1));
                user.setUsername(record.get(2));
                user.setPassword(record.get(3));

                Name name = new Name();
                name.setfirstName(record.get(4));
                name.setlastName(record.get(5));
                user.setName(name);

                Address address = new Address();
                address.setCity(record.get(6));
                address.setStreet(record.get(7));
                address.setNumber(Integer.parseInt(record.get(8)));
                address.setZipcode(record.get(9));
                user.setAddress(address);


                Geolocation geolocation = new Geolocation();
                geolocation.setLat(record.get(10));
                geolocation.setLongi(record.get(11));
                address.setGeolocation(geolocation);
                user.setPhone(record.get(12));
                users.add(user);
            }
        }
        catch(NumberFormatException e){
            throw new Exception("Invalid format at record index "+ i + ":"+ e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new Exception("Array index " + i + "is out of access "+ ":"+ e.getMessage());
        }
        return users;

    }
    /**
     * Returns a specific user from Users list by its index.
     * @param users The list of users
     * @param index index of user
     * @return User object.
     */

    public static User getUserFromListByIndex(List<User> users,int index){
        return users.get(index);
    }
    /**
     * Maps a list of lists of strings from a data file to a list of User objects.
     * @param records The list of lists of strings to be mapped.
     * @return List<Product> A list of User objects.
     * @throws Exception If the number of fields in the data file is incorrect.
     */

    public static List<User> mapListToUsers(List<List<String>> records) {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < records.size(); i++) {
            User user = new User();
            user.setId(Integer.parseInt(records.get(i).get(0)));
            user.setEmail(records.get(i).get(1));
            user.setUsername(records.get(i).get(2));
            user.setPassword(records.get(i).get(3));
            Name name = new Name(records.get(i).get(4), records.get(i).get(5));
            user.setName(name);
            Geolocation geolocation = new Geolocation(records.get(i).get(10), records.get(i).get(11));
            Address address = new Address(records.get(i).get(6),
                    records.get(i).get(7),
                    Integer.parseInt(records.get(i).get(8)),
                    records.get(i).get(9), geolocation);
            user.setAddress(address);

            users.add(user);

        }
        return users;
    }
}
