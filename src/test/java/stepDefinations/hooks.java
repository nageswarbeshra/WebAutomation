package stepDefinations;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class hooks {
	
	@Before("@NetbankingTest")
	public void netBankingSetup()
	{
		System.out.println("########################");
		System.out.println("Setup the entries in netBanking database");
	}
	
	@After
	public void tearDown()
	{
		System.out.println("Clear Entities");
	}
	
	@Before("@MortageTest")
	public void mortage()
	{
		System.out.println("########################");
		System.out.println("Setup the entries in mortage database");
	}

}
