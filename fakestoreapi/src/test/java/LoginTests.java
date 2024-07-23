import org.api.clients.RestClient;
import org.api.models.api.HTTPResponse;
import org.api.models.api.TokenResponse;
import org.api.models.user.User;
import org.api.reports.ExtentReportsListeners;
import org.api.services.LoginService;
import org.api.services.UserService;
import org.api.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
@Listeners(ExtentReportsListeners.class)
public class LoginTests {
    LoginService service;
    UserService userService;
    @BeforeSuite
    private void Setup(){
        service=new LoginService();
        userService=new UserService();

    }

    @Test(dataProvider = "getValidUserId")
    private void verifyValidLogin(int Id) throws RestClient.HttpRequestException, IOException {
        userService =new UserService();
        User user=userService.getUserById(Id);
        HTTPResponse tokenResponse=service.Login(user.getUsername(),user.getPassword());
        TokenResponse token=Utils.mapResponseToObject(tokenResponse,TokenResponse.class);
        Assert.assertNotNull(token.getToken());
    }
    @Test(dataProvider = "getValidUserId")
    private void verifyInvalidPassword(int Id) throws RestClient.HttpRequestException, IOException {
        userService =new UserService();
        User user=userService.getUserById(Id);
        TokenResponse token = null;
        HTTPResponse tokenResponse = null;
        try {
            tokenResponse = service.Login(user.getUsername(), "hjhjshjhj");
        }catch (RestClient.HttpRequestException e){
            Assert.assertEquals(e.getMessage(),"Login Failed 401");

        }
    }
    @Test(dataProvider = "getValidUserId")
    private void verifyInvalidUsername(int Id) throws RestClient.HttpRequestException, IOException {
        userService =new UserService();
        User user=userService.getUserById(Id);
        TokenResponse token = null;
        HTTPResponse tokenResponse = null;
        try {
            tokenResponse = service.Login("johna", user.getPassword());
            token = Utils.mapResponseToObject(tokenResponse, TokenResponse.class);
        }catch (RestClient.HttpRequestException e){
            Assert.assertEquals(e.getMessage(),"Login Failed 401");

        }
    }
    @Test(dataProvider = "getValidUserId")
    private void verifyUnsuccessfullLoginWithoutPassword(int Id) throws RestClient.HttpRequestException, IOException {
        userService =new UserService();
        User user=userService.getUserById(Id);
        HTTPResponse tokenResponse = null;
        try {
            tokenResponse = service.Login(user.getUsername(), "");
        }catch (RestClient.HttpRequestException e){
            Assert.assertEquals(e.getMessage(),"Login Failed 400");

        }
    }
    @Test
    private void verifyUnsuccessfullLoginWithoutUsernameAndPassword() throws RestClient.HttpRequestException, IOException {
        HTTPResponse tokenResponse = null;
        try {
            tokenResponse = service.Login("", "");
        }catch (RestClient.HttpRequestException e){
            Assert.assertEquals(e.getMessage(),"Login Failed 400");

        }
    }

    @DataProvider
    public Object[][] getValidUserId(){
        return new Object[][]{
                {1},
                {2},{3},{4},{5}
        };

    }
}
