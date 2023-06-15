import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class AutomationProject2 {
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        Faker faker = new Faker();
        RandomVal randomVal = new RandomVal();
        Random random = new Random();
        String firstAndLast = randomVal.randomFirstAndLastName();
        String streetN = randomVal.streetN();
        String city = randomVal.randomCity();
        String state = randomVal.state();
        String zip = String.valueOf(randomVal.zip());
        double discount = 0.0;
        int pricePerUnit = 100;

        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

        // driver.manage().window().maximize();


        //enter login credentials
        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");

        //click login
        driver.findElement(By.id("ctl00_MainContent_login_button")).click();

        //click orders
        driver.findElement(By.xpath("//a[@href='Process.aspx']")).click();
        Thread.sleep(1000);

        String product = driver.findElement(By.id("ctl00_MainContent_fmwOrder_ddlProduct")).getAttribute("value");


        //driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys( "10");

        //enter random quantity
        String randomQuantity = String.valueOf(randomVal.random3DigNum());

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys(Keys.BACK_SPACE, randomQuantity);
        //driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys(Keys.BACK_SPACE, "8");

        //click calculate
        driver.findElement(By.xpath("//input[@type= 'submit']")).sendKeys(Keys.ENTER);

        //the product quantity
        int actualQuantity = Integer.parseInt(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).getAttribute("value"));

        //verify the product price per unit is 100
        int productPrice = Integer.parseInt(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtUnitPrice")).getAttribute("value"));

        // Verify the discount amount
        int actualDiscount = Integer.parseInt(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtDiscount")).getAttribute("value"));


        //verify the total
        int actualTotal = Integer.parseInt(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtTotal")).getAttribute("value"));

        if (actualQuantity > 10) {
            discount = 0.08;
        } else {
            discount = 0.0;
        }



        //verify if the expected value equals to actual
        int expectedTotal = (int) ((actualQuantity * pricePerUnit) - (actualQuantity * pricePerUnit * discount));
        Assert.assertEquals(actualTotal, expectedTotal);

        //find and enter the customers name
        driver.findElement(By.xpath("//input[@name= 'ctl00$MainContent$fmwOrder$txtName']")).sendKeys(firstAndLast);

        //find and enter the street
        driver.findElement(By.xpath("//input[@name='ctl00$MainContent$fmwOrder$TextBox2']")).sendKeys(streetN);

        // enter the city
        driver.findElement(By.xpath("//input[@name='ctl00$MainContent$fmwOrder$TextBox3']")).sendKeys(city);

        //state
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys(state);

        //zip
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys(zip);

        //

        //select the random one
        WebElement[] cardType = new WebElement[]{
                driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_0")),
                driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_1")),
                driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_2"))

        };


        WebElement selected = null;
        int randomIndex = random.nextInt(cardType.length);

        cardType[randomIndex].click();

        //verify which one is checked
        String selectedCardTypeName = "";

        for (int i = 0; i < cardType.length; i++) {
            boolean isSelected = cardType[i].isSelected();

            if (isSelected) {
                selected = cardType[i];
                selectedCardTypeName = cardType[i].getAttribute("value");
                break;
            }

        }


        String VISA_N = faker.finance().creditCard(CreditCardType.VISA);
        String MASTERCARD_N = faker.finance().creditCard(CreditCardType.MASTERCARD);
        String AMERICAN_EXPRESS_N = faker.finance().creditCard(CreditCardType.AMERICAN_EXPRESS);

        String selectedCardN = "";

        if (selected.getAttribute("value").equalsIgnoreCase("Visa")) {
            selectedCardN = VISA_N.replace("-","");
        } else if (selected.getAttribute("value").equalsIgnoreCase("MasterCard")) {

            selectedCardN = MASTERCARD_N.replace("-","");

        } else if (selected.getAttribute("value").equalsIgnoreCase("American Express")) {

            selectedCardN = AMERICAN_EXPRESS_N.replace("-", "");

        }

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(selectedCardN);
            //Current date
            LocalDate currentDate = LocalDate.now();

            // Generate a random expiration date that is newer than the current date
            LocalDate randomExpirationDate = currentDate.plusMonths(1 + (long)(Math.random() * 24));  // Add 1 to 24 months

            // Format the expiration date as MM/yy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            String formattedExpirationDate = randomExpirationDate.format(formatter);

            driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).sendKeys(formattedExpirationDate);

            DateTimeFormatter formatterCurrentDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formattedCurrentDate = currentDate.format(formatterCurrentDate);

      //  click process
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();

        // Get the page source
        String pageSource = driver.getPageSource();

        //Verify the page contains the text
        Assert.assertTrue(pageSource.contains("New order has been successfully added"));

        //Navigate to View all orders
        driver.findElement(By.xpath("//a[@href ='Default.aspx']")).click();


        List expectedTableVal = List.of(firstAndLast, product, randomQuantity,formattedCurrentDate, streetN, city, state, zip, selectedCardTypeName, selectedCardN, formattedExpirationDate);

        List<String> actualTableVal = new ArrayList<>();
        for (int i = 2; i < 13; i++) {
            actualTableVal.add(driver.findElement(By.xpath("//table[@class='SampleTable']//td[" + i + "]")).getText());
        }

        Assert.assertEquals(actualTableVal, expectedTableVal);
        System.out.println();
        System.out.println(expectedTableVal);
        System.out.println(actualTableVal);

        driver.findElement(By.id("ctl00_logout")).click();

    }
    }

