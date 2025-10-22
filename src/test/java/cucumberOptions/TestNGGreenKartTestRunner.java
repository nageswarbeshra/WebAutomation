package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

	
@CucumberOptions(features="src/test/java/greenKartfeatures",glue="greenKartstepDefinations",monochrome=true,tags="not @NobileTest")
public class TestNGGreenKartTestRunner extends AbstractTestNGCucumberTests {
		
		

}
