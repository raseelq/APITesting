package users;

import org.api.constants.Constants;
import org.api.interfaces.IUserInterface;
import org.api.models.user.User;
import org.api.services.UserService;
import org.api.utils.Utils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.util.List;

public class UserBaseClass {
    IUserInterface userService;
    List<User> usersList;
    @BeforeTest
    protected void setup() throws Exception {
        userService =new UserService();
        String resourcesFilePath=this.getClass().getClassLoader().getResource(Constants.USERS_SOURCE_FILE).getFile();
        List<List<String>> records= Utils.readCSVResourceFile(resourcesFilePath,",");
        usersList=userService.mapListToUsers(records);
    }
}
