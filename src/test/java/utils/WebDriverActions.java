package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class WebDriverActions {

    private WebDriver driver;

    public WebDriverActions(WebDriver driver) {
        this.driver = driver;
    }

    public void personalInformation() throws InterruptedException, IOException {
        if (ReusableMethods.getGlobalValue("lender").equalsIgnoreCase("axio")) {
            Thread.sleep(2000);
            WebElement pan = driver.findElement(By.xpath("//*[@placeholder='10 Digit PAN']"));
            pan.click();
            pan.sendKeys("XYZPM1234M");//later get from reusable methods
            Thread.sleep(2000);
            WebElement firstName = driver.findElement(By.xpath("//*[@placeholder='First Name']"));
            firstName.click();
            firstName.sendKeys("AMOGAYA");//later get from reusable methods
            Thread.sleep(2000);
            WebElement lastName = driver.findElement(By.xpath("//*[@placeholder='Last Name']"));
            lastName.click();
            lastName.sendKeys("WADAYAR");//later get from reusable methods
            Thread.sleep(2000);
            WebElement pincode = driver.findElement(By.xpath("//*[@placeholder='6 Digit Pincode']"));
            pincode.click();
            pincode.sendKeys("560037");//later to be changed from reusable methods
            Thread.sleep(3000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(5000);
            driver.findElement(By.xpath("//article[contains(text(),'Residence type')]")).click();
            Thread.sleep(5000);
            driver.findElement(By.xpath("//article[contains(text(),'Rented')]")).click();
            Thread.sleep(5000);
            driver.findElement(By.xpath("//article[contains(text(),'Education qualification')]")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//article[contains(text(),'Undergraduate')]")).click();
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(3000);
            driver.findElement(By.xpath("//article[contains(text(),'Single')]")).click();
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(3000);
            driver.findElement(By.xpath("//article[contains(text(),'Male')]")).click();
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(3000);
            clickNext();
            financialInformation();
        }
    }

    public void financialInformation() throws InterruptedException, IOException {
        Thread.sleep(3000);
        WebElement monthlyIncome = driver.findElement(By.xpath("//*[@placeholder='0']"));
        monthlyIncome.click();
        if (ReusableMethods.getGlobalValue("lender").equalsIgnoreCase("axio"))  monthlyIncome.sendKeys("25000");
        else monthlyIncome.sendKeys("45000"); // later to change
        Thread.sleep(3000);
        driver.findElement(By.xpath("//article[contains(text(),'Salaried')]")).click();
        Thread.sleep(5000);
        clickNext();
        additionalInformation();
    }

    public void additionalInformation() throws InterruptedException {
        Thread.sleep(3000);
        WebElement fathersname = driver.findElement(By.xpath("//*[@placeholder=\"Father's Name\"]"));
        fathersname.click();
        fathersname.sendKeys("Father's Name");
        Thread.sleep(3000);
        clickNext();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//article[contains(text(),'Preview Details')]"));
        clickNext();

    }

    public void fetchOffer() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath("//article[contains(text(),'Fetch Offer')]")).click();
        Thread.sleep(3000);
    }


    public void getOffers() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath("//article[contains(text(),'Get offers')]"));
        Thread.sleep(5000);
        try {
            driver.findElement(By.xpath("//article[contains(text(),'AXIO')]")).click();
            Thread.sleep(3000);
            fetchOffer();
        } catch (NoSuchElementException ec) {
            driver.findElement(By.xpath("//article[contains(text(),'Mock Lender')]")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//article[contains(text(),'Fetch Offer')]")).click();
            Thread.sleep(3000);
            try {
                driver.findElement(By.xpath("//img[@alt='filled square checkbox']"));
            } catch (NoSuchElementException ec2) {
                driver.findElement(By.xpath("//img[@alt='unfilled square checkbox']")).click();
                Thread.sleep(2000);
            }
            driver.findElement(By.xpath("//article[contains(text(),'Proceed')]")).click();
            Thread.sleep(180000);
            driver.findElement(By.xpath("//article[contains(text(),'More lenders')]")).click();
            Thread.sleep(2000);
        }
        driver.findElement(By.xpath("//article[contains(text(),'AXIO')]")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//article[contains(text(),'Fetch Offer')]")).click();
        Thread.sleep(3000);

    }

    public void startApplication() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.xpath("//article[contains(text(),'Start a new Application')]")).click();
    }

    public void previewDetails() throws InterruptedException {
        Thread.sleep(3000);
        clickNext();
        getOffers();
    }

    public void clickNext() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.xpath("//article[contains(text(),'Next')]")).click();
    }

    public void navigateToPaymentUrl(String url) throws InterruptedException, IOException {
        driver.get(url);
        UserAgent agent = new UserAgent();
        String FEagent = (ReusableMethods.getGlobalValue("user-agent"));
        agent.setAgent(FEagent, driver);
        driver.manage().window().fullscreen();
        if (!ReusableMethods.getGlobalValue("user-agent").equalsIgnoreCase("BETA")) {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//article[contains(text(),'Loan Offers')]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//img[@alt='unfilled square checkbox']")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//article[contains(text(),'Continue')]")).click();
            Thread.sleep(5000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//     for resume state if the user wants to continue the journey as of now we are directly starting new journey
            try{
                WebElement resumeState = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article[contains(text(),'You have already applied for a loan and these are the details:')]")));
                startApplication();
            }
//      this is for, if user has completed profile info and wants to continue
            catch (TimeoutException eg) {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article[contains(text(),'Preview Details')]")));
                    previewDetails();
                } catch (TimeoutException e) {
                    try {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article[contains(text(),'Personal Information')]")));
                        personalInformation();
                    } catch (TimeoutException e2) {
                        try {
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article[contains(text(),'Financial Information')]")));
                            financialInformation();
                        } catch (TimeoutException e3) {
                            try {
                                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//article[contains(text(),'Additional Information')]")));
                                additionalInformation();
                            } catch (TimeoutException e4) {
                                System.out.println("At here in last catch");
                            }
                        }
                    }
                }
            }
        }
    }

    public void closeDriver() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(5000);
            driver.close();
        }
    }
}
