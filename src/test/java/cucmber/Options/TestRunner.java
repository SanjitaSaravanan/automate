package cucmber.Options;

import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@io.cucumber.junit.CucumberOptions(
        features = "src/test/java/features/starhealth.feature",
        glue = "stepdefinition",
        monochrome = true,
        dryRun = false
        //		plugin= {"html:target/cucumber.html", "json:target/cucumber.json",
//"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
//"rerun:target/failed_scenarios.txt"}

)
public class TestRunner {
}
