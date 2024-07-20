package org.api.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class ExtentReportsListeners {
        ExtentReports extent;
        ExtentTest test;
    @BeforeSuite
    public void setup(){
            this.extent=new ExtentReports();
            extent.attachReporter(new ExtentSparkReporter("results.html"));
        }
    @BeforeMethod
    public void beforeMethod(Method method){
        test=extent.createTest(method.getName());
    }
    @AfterMethod
    public void afterMethod(ITestResult result){
        if(result.getStatus()==ITestResult.FAILURE){
            test.fail(result.getThrowable());
        }else if(result.getStatus()==ITestResult.SUCCESS){
            test.pass("Test Passed");
        }

    }
    @AfterSuite
    public void teardown(){
        extent.flush();
    }



}
