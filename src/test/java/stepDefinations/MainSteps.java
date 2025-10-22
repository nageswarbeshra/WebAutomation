package stepDefinations;

import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MainSteps {
	
	
	@Given("User is on the Netbanking landing page")
	public void user_is_on_the_netbanking_landing_page() {
	    // Write code here that turns the phrase above into concrete actions
		
		System.out.println("User is on the practice landing pagee");
	    
	}
	@When("User login into application with {string} and password  {string}")
	public void user_login_into_application_with_and_password(String username, String password) {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("User login into application with \nusername: "+ username+"\nand password : "+password);
	}
	
	
	@Given("User is on the practice landing page")
	public void user_is_on_the__landing_page() {
	    // Write code here that turns the phrase above into concrete actions
		
		System.out.println("User is on the practice landing pagee");
	    
	}
	@Given("User is on the mortage landing page")
	public void user_is_on_the__Mortage_page() {
	    // Write code here that turns the phrase above into concrete actions
		
		System.out.println("User is on the mortage landing page");
	    
	}
	
	
	
	@When("User sing up into application")
	public void user_sing_up_into_application(List<String> dataTable) {

		System.out.println(dataTable.get(0));
		System.out.println(dataTable.get(1));
		System.out.println(dataTable.get(2));
		System.out.println(dataTable.get(3));
	}
	@Then("Home Page is displayed")
	public void home_page_is_displayed() {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Home Page is displayed");
	   
	}
	@Then("Cards are displayed")
	public void cards_are_displayed() {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Cards are displayed");
	    
	}
	
	
	@Given("Setup the entries in database")
	public void setupentries()
	{
		System.out.println("*************************");
		System.out.println("Setup the entries in database");
	}
	@When("Launch the browser from config variables")
	public void launchbrowser()
	{
		
		System.out.println("Launch the browser from config variables");
	}
	@Given("hit the page url of banking site")
	public void hiturl()
	{

		System.out.println("hit the page url of banking site");
	}

}
