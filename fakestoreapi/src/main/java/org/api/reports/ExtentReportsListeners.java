package org.api.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ExtentReportsListeners implements ITestListener, ISuiteListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<String,ExtentTest> testClassNodes=new HashMap<>();


    @Override
    public void onStart(ISuite suite) {
        extent= new ExtentReports();
        extent.attachReporter(new ExtentSparkReporter("results.html"));

    }

    @Override
    public void onFinish(ISuite suite) {
        if(extent!=null){
            extent.flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String className=result.getTestClass().getName();
        ExtentTest classNode=testClassNodes.get(className);
        if(classNode==null){
            classNode=extent.createTest(className);
        }
        testClassNodes.put(className,classNode);
        ExtentTest methodNode=classNode.createNode(result.getMethod().getMethodName());
        test.set(methodNode);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed!");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
    }
    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
    }

}
