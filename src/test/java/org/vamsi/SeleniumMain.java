package org.vamsi;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.vamsi.utils.CommonUtilities;

import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SeleniumMain {

    static Logger logger = Logger.getLogger(SeleniumMain.class.getName());
    public static WebDriver setupDriver(){
        WebDriver driver=null;
        try{
            System.setProperty("webdriver.chrome.driver", "C:/Drivers/chromedriver-win64/chromedriver.exe");
            driver = new ChromeDriver();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  driver;
    }


    public static void main(String[] args) {
        WebDriver driver=null;
        try {
            String propFilePath = System.getProperty("user.dir")+"/src/test/resources/config.properties";
            Properties props = CommonUtilities.loadPropFile(propFilePath);

            driver = setupDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            /*
                driver.navigate().to(props.getProperty("website").trim());
                String actSiteNameVal = driver.findElement(By.xpath(props.getProperty("siteName").trim())).getText().trim();
                logger.info(String.format("[%s] Actual site name = %s", SeleniumMain.class.getName(), actSiteNameVal));
                Assert.assertEquals(actSiteNameVal, "Selenium Easy", "Site name didn't match");
            */

            DatePickerJ.pickDateFromCalendar(props, driver, "20230323");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (driver != null){
                driver.close();
            }
        }

    }
}
