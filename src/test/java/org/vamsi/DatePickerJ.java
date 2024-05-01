package org.vamsi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DatePickerJ {

    static Logger logger = Logger.getLogger(DatePickerJ.class.getName());
    public static void pickDateFromCalendar(Properties props, WebDriver driver, String dateToPick){

        try {
            //navigate to url
            driver.get(props.getProperty("datePickerUrl"));

            //click on the calendar icon - calendar shows up.
            driver.findElement(By.xpath(props.getProperty("calendarIcon"))).click();
            System.out.println("Clicked on calendar icon");

            //format the date and divide year, month and date from given dateToPick.
            DateTimeFormatter formatter =DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate date = LocalDate.parse(dateToPick,formatter );
            String month= date.getMonth().name();
            int year = date.getYear();
            int day = date.getDayOfMonth();
            logger.info(String.format("%s, %s, %s", year, month, day));

            //prev and next button on calendar
            WebDriverWait wait = new WebDriverWait(driver, 5);

            WebElement prevButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(props.getProperty("prevButton"))));
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(props.getProperty("nextButton"))));

            String yearFromUi;
            boolean isYearSame = false;

            //click on prev or next button till the year matches.
            while(!isYearSame){

                yearFromUi = driver.findElement(By.xpath(props.getProperty("yearElement"))).getText().trim();

                if(year > Integer.parseInt(yearFromUi)){
                    Thread.sleep(2000);
                    prevButton.click();
                    System.out.println("Prev clicked");
                } else if (year < Integer.parseInt(yearFromUi)) {
                    Thread.sleep(2000);
                    nextButton.click();
                    System.out.println("Next clicked");
                }

                isYearSame = year == Integer.parseInt(yearFromUi);

                //if year matches start matching months.
                if(isYearSame){
                    logger.info(String.format("Year from UI: [%s], Year expected: [%s]", yearFromUi, year));
                    String monthFromUi = driver.findElement(By.xpath(props.getProperty("monthElement"))).getText().trim();
                    System.out.println("monthFromUi = " + monthFromUi);
                    boolean isMonthSame = month.equalsIgnoreCase(monthFromUi);

                    //iterate till month becomes expected.
                    while(!isMonthSame){
                        System.out.println("Month not same= "+monthFromUi);
                        monthFromUi = driver.findElement(By.xpath(props.getProperty("monthElement"))).getText().trim();
                        int monthFromUiValue = Month.valueOf(monthFromUi.toUpperCase()).getValue();
                        int monthExpValue = Month.valueOf(month.toUpperCase()).getValue();

                        if(monthFromUiValue > monthExpValue){
                            Thread.sleep(2000);
                            prevButton.click();
                            System.out.println("Prev clicked");
                        }else if (monthFromUiValue < monthExpValue){
                            Thread.sleep(2000);
                            nextButton.click();
                            System.out.println("Next clicked");
                        }

                        isMonthSame =month.equalsIgnoreCase(monthFromUi);

                        if(isMonthSame){
                            logger.info(String.format("Month from UI: [%s], Month expected: [%s]", monthFromUi, month));
                            String dateXpath = String.format(props.getProperty("dayElement"), day);
                            System.out.println("dateXpath = " + dateXpath);
                            WebElement dateEl = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath(dateXpath)));
                            dateEl.click();
                        }
                    }
                }
            }
        }
        catch (Exception e){
            logger.info(e.getMessage());
            e.printStackTrace();
        }

    }

}
