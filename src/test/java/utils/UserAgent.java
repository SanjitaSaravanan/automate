package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v113.network.Network;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserAgent {

   public void setAgent(String agent, WebDriver driver)
   {
       String userAgent = "";
       if(agent.equalsIgnoreCase("CUG"))
       {
           userAgent = "Hyper/track=cug";
       } else if (agent.equalsIgnoreCase("beta")) {
           userAgent = "Hyper/track=beta";
       }
       else{
           userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36";
       }
       Map<String, Object> params = new HashMap<>();
       params.put("userAgent", userAgent);

       ((ChromeDriver) driver).executeCdpCommand("Network.setUserAgentOverride", params);
       String currentUrl = driver.getCurrentUrl();
       driver.navigate().refresh(); // This refreshes the current page
       driver.get(currentUrl);
   }
}
