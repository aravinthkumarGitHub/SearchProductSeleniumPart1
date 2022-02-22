package com.crm.qa.testcases;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.HomePageStore;
import com.crm.qa.pages.SearchResultsPage;
import com.crm.qa.pages.TshirtsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutomateSearchProductTest extends TestBase {

    HomePageStore homePageStore;
    TshirtsPage tshirtsPage;
    SearchResultsPage searchResultsPage;
    public String productName;


    public AutomateSearchProductTest(){
        super();
    }

    @BeforeMethod
    public void setUp(){
        initialization();
        homePageStore = new HomePageStore();
    }

    @Test(priority=1)
    public void searchProductTest(){
        boolean flag = homePageStore.validateStoreLogo();
        Assert.assertTrue(flag,"Logo is not displayed");
        tshirtsPage = homePageStore.clickOnTshirtLink();
        tshirtsPage.verifySearchResults();
        productName = tshirtsPage.getFirstProduct();
        System.out.println(productName +"Check");
        tshirtsPage.enterSearchTerm(productName);
        searchResultsPage = tshirtsPage.clickOnSearchIcon();
        searchResultsPage.verifyResultsList();
        Assert.assertTrue(productName.equalsIgnoreCase(searchResultsPage.getFirstResultName()));
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
