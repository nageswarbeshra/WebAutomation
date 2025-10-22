package greenKartstepDefinations;

import java.util.Iterator;
import java.util.Set;

import javax.swing.text.TabStop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import driverSetup.BrowserSetup;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class greenKartstepdefiantions extends BrowserSetup {
public WebDriver driver;
public String productName;
public String actualProductName;
	
	@Given("User is on GreeenCart Landing page")
	public void user_is_on_greeen_cart_landing_page() throws Exception {
		
		
		driver = setup_driver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
	    // Write code here that turns the phrase above into concrete actions
	}
	@When("User search with Shortname {string} and extracted actual name of product")
	public void user_search_with_shortname_and_extracted_actual_name_of_product(String shortName) throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
		driver.findElement(By.xpath("//input[@class='search-keyword']")).sendKeys(shortName);
		Thread.sleep(2000);
		productName =   driver.findElement(By.xpath("//h4[@class='product-name']")).getText().split("-")[0].trim();
		System.out.println(productName);
	}
	@Then("user search for same shortname in offers page to check if the product exist")
	public void user_search_for_same_shortname_in_offers_page_to_check_if_the_product_exist() {
		driver.findElement(By.linkText("Top Deals")).click();
		Set<String> s1 =    driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		String parentWindow = i1.next();
		String childWindowr = i1.next();
		driver.switchTo().window(childWindowr);
		driver.findElement(By.xpath("//input[@id=\"search-field\"]")).sendKeys("Tom");
		String actualProductName = driver.findElement(By.xpath("//tr[td[1][text()=\"Tomato\"]]")).getText().split(" ")[0].trim();
		System.out.println(actualProductName);
		Assert.assertEquals(productName, actualProductName);
		
		driver.quit();
		
		
		
	    // Write code here that turns the phrase above into concrete actions
	   
	}

}
