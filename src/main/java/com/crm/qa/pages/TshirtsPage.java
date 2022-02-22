package com.crm.qa.pages;

import com.crm.qa.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.util.List;

public class TshirtsPage extends TestBase {

    public TshirtsPage() {
        PageFactory.initElements(driver, this);
    }
    public String nameOfProduct;

    @FindBy(xpath="//*[@id=\"center_column\"]/ul/li/div/div/h5/a")
    List<WebElement> searchResults;

    @FindBy(id="search_query_top")
    WebElement searchBox;

    @FindBy(xpath="//button[@name='submit_search']")
    WebElement searchButton;


    public void verifySearchResults(){
        waitForWebElementToBeVisible(searchResults.stream().findFirst().get());
        if(searchResults.stream().findFirst().get().isDisplayed()){
            Reporter.log("Search Results are displayed");
            WebElement firstResult = searchResults.stream().findFirst().get();
            scrollToElement(firstResult);
            setImplicitWait();
        }

    }

    public String getFirstProduct() {
        if (searchResults.size() > 0) {
            waitForWebElementToBeVisible(searchResults.stream().findFirst().get());
            nameOfProduct = searchResults.stream().findFirst().get().getAttribute("title");
        }
        return nameOfProduct;
    }

    public void enterSearchTerm(String productTitle){
        waitForWebElementToBeVisible(searchBox);
        scrollToElement(searchBox);
        setImplicitWait();
        setValueToTextBox(searchBox,productTitle);
        setImplicitWait();
    }

    public SearchResultsPage clickOnSearchIcon(){
        waitForWebElementToBeVisible(searchButton);
        setImplicitWait();
        clickElement(searchButton);
        setImplicitWait();
        return new SearchResultsPage();
    }
}
