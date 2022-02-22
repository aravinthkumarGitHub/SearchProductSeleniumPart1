package com.crm.qa.pages;

import com.crm.qa.base.TestBase;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.util.List;

public class HomePageStore extends TestBase {


    @FindBy(xpath="//div/a/img[@alt='My Store']")
    WebElement pageLogo;

    @FindBy(xpath="//li/a[@title='Women']")
    WebElement womenTab;

    @FindBy(xpath="//ul/li/a[@title='T-shirts']")
    List<WebElement> tShirts;

    @FindBy(xpath="//*[@id=\"block_top_menu\"]/ul/li/ul/li/ul/li/a")
    List<WebElement> topMenu;

    public HomePageStore(){
        PageFactory.initElements(driver, this);
    }

    public boolean validateStoreLogo(){
        boolean flag = false;
        try {
            if(pageLogo.isDisplayed()){
                flag = pageLogo.isDisplayed();
                Reporter.log("Store Home page is displayed");
                return flag;
            }
        } catch(Exception e) {
            Reporter.log("Store Home page is not displayed");
        }
        return flag;
    }

    public void hoverOverWomenTab(){
        waitForWebElementToBeVisible(womenTab);
        mouseHoverOverElement(womenTab);
    }

    public TshirtsPage clickOnTshirtLink(){
        try {
            hoverOverWomenTab();
            waitForWebElementToBeVisible(topMenu.stream().filter(x -> x.isDisplayed()).findFirst().get());
            WebElement tShirtsLink = topMenu.stream().filter(x->x.getAttribute("title").equalsIgnoreCase("T-shirts")).findFirst().get();
            clickElement(tShirtsLink);
        }catch(StaleElementReferenceException e) {
            Reporter.log("T-Shirts link is not clicked" + e.getStackTrace());
        }
        return  new TshirtsPage();
    }
}
