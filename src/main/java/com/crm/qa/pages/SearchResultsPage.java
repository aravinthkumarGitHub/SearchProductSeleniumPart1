package com.crm.qa.pages;

import com.crm.qa.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.util.List;

public class SearchResultsPage extends TestBase {

    public SearchResultsPage() {
        PageFactory.initElements(driver, this);
    }
    public String nameOfFirstResult;

    @FindBy(xpath="//span[contains(text(),'result has been found')]")
    WebElement resultsHeader;

    @FindBy(xpath="//*[@id=\"center_column\"]/ul/li/div/div/div/a/img")
    List<WebElement> resultsList;

    public void verifyResultsList(){
        waitForWebElementToBeVisible(resultsList.stream().findFirst().get());
        if(resultsList.stream().findFirst().get().isDisplayed()){
            Reporter.log("Search Results are displayed");
            WebElement firstResult = resultsList.stream().findFirst().get();
            scrollToElement(firstResult);
            setImplicitWait();
        }
    }

    public String getFirstResultName() {
        if (resultsList.size() > 0) {
            waitForWebElementToBeVisible(resultsList.stream().findFirst().get());
            nameOfFirstResult = resultsList.stream().findFirst().get().getAttribute("title");
            setImplicitWait();
        }
        return nameOfFirstResult;
    }
}


