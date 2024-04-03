package GlobalGets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;

public class GlobalFF {
	public FirefoxDriver driver;
	public String nom=randomString();
	public String prenom= randomString();
	public String operationa;
	public String mail="amir.sghaier@infopro-digital.com";
	public String mp="Welcome2020";
	public static Properties prop;
	public GlobalFF() throws IOException {
		prop= new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\amir.sghaier\\Desktop\\workspace\\IncidentManagement\\src\\test\\java\\config\\config.properties");
		prop.load(fis);
	}
//	@Parameters("browser")

	public void login() throws InterruptedException {
//		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
//		ChromeOptions ChromeOptions = new ChromeOptions();
//	ChromeOptions.setHeadless(true); //ou bien options.addArguments("--headless");
//		driver = new ChromeDriver(ChromeOptions);
//		driver.manage().window().maximize();
		driver.get("https://www.test.hse-compliance.net/");
		driver.findElement(By.id("txtUserName")).sendKeys("amir.sghaier@infopro-digital.com");
		driver.findElement(By.id("txtUserPass")).sendKeys("Welcome2020");
		driver.findElement(By.id("meConnecter")).click();
//		if(browser.equalsIgnoreCase("firefox")){
//			//create firefox instance
//			System.setProperty("webdriver.Firefox.driver", "geckodriver.exe");
//			FirefoxOptions FireOptions = new FirefoxOptions();
//			//FireOptions.setHeadless(true);
////			FireOptions.addArguments("--headless");
//			driver = new FirefoxDriver();
//			driver.manage().window().maximize();
//			driver.get("https://www.test.hse-compliance.net/");
//			driver.findElement(By.id("txtUserName")).sendKeys("amir.sghaier@infopro-digital.com");
//			driver.findElement(By.id("txtUserPass")).sendKeys("Welcome2020");
//			driver.findElement(By.id("meConnecter")).click();
//		}
//		//Check if parameter passed as 'chrome'
//		else if(browser.equalsIgnoreCase("chrome")){
//			//set path to chromedriver.exe
//			System.setProperty("webdriver.chrome.driver","chromedriver.exe");
//			ChromeOptions ChromeOptions = new ChromeOptions();
////			ChromeOptions.setHeadless(true); //ou bien options.addArguments("--headless");
//			//create chrome instance
//			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("https://www.test.hse-compliance.net/");
			driver.findElement(By.id("txtUserName")).sendKeys("amir.sghaier@infopro-digital.com");
			driver.findElement(By.id("txtUserPass")).sendKeys("Welcome2020");
			driver.findElement(By.id("meConnecter")).click();
		}
	
	public void ClickIM() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		WebElement menuBurger= driver.findElement(By.id("jqMenuIcon")); 
		menuBurger.click();
		Thread.sleep(1500);
		WebElement incidentManagement= driver.findElement(By.id("incident-management")); incidentManagement.click();
		String expectedUrl="https://www.test.hse-compliance.net/app/incident-management";
		assertEquals(expectedUrl,driver.getCurrentUrl());
	}
	public void sousMenuIM(String menu) throws InterruptedException {
		WebElement clickSousMeu=	driver.findElement(By.xpath("//button[@class='v-app-bar__nav-icon v-btn v-btn--icon v-btn--round theme--light v-size--default white--text']"));
		clickSousMeu.click();
		if (menu=="tableauBord") {
			WebElement tableauBord=driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/aside/div[1]/div/div[1]/div[2]/a[1]/div"));
			tableauBord.click();
			assertEquals(driver.getCurrentUrl(), "https://www.test.hse-compliance.net/app/incident-management");
		}
		else if (menu=="incidentList") {
			WebElement incidentList=driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/aside/div[1]/div/div[1]/div[2]/a[2]/div"));	
incidentList.click();
Thread.sleep(500);
WebDriverWait wait = new WebDriverWait(driver,8);
wait.until(ExpectedConditions.urlToBe("https://www.test.hse-compliance.net/app/incident-management/events-list"));
assertEquals(driver.getCurrentUrl(), "https://www.test.hse-compliance.net/app/incident-management/events-list");
		}
		else if (menu=="createIncident") {
			WebElement createIncident= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/aside/div[1]/div/div[1]/div[2]/div/div"));
			createIncident.click();
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.urlContains("https://www.test.hse-compliance.net/app/incident-management/events/"));
			String currentUrl=driver.getCurrentUrl() ;
			boolean verifUrl=Pattern.compile("^https://www.test.hse-compliance.net/app/incident-management/events/").matcher(currentUrl).find();
			assertTrue(verifUrl,"Create a new incident URL is wrong");
		}
		else if (menu=="monthlyData") {
			WebElement monthlyData= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/aside/div[1]/div/div[1]/div[2]/a[3]/div"));
			monthlyData.click();
			assertEquals(driver.getCurrentUrl(), "https://www.test.hse-compliance.net/app/incident-management/monthly-data");
		}
		
	}

	public static String randomString() {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'

		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString; 
	}
	public void telecommande(String url, String profil , String site) throws InterruptedException {
int x=0;
if (url=="dashboard") {
			driver.findElement(By.xpath("/html/body/div[2]/div[2]/button")).click();
		x=2;
		}
		else if (url=="incidentList") {
			driver.findElement(By.xpath("//body/div[1]/div[2]/button[1]")).click();
		x=1;
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement vueListe= driver.findElement(By.xpath("//button[@class='buttonType2 telecommande__iconButton']"));
		vueListe.click();
		if (profil=="admin") //Administrateur
		{
			driver.findElement(By.xpath("//div[@name='Administrateur']")).click();
		}	
		else if (profil=="superadmin") //Super Administrateur
		{
			driver.findElement(By.xpath("//div[@name='Super Administrateur']")).click();
		}
		else if (profil=="correspHSE") //Correspondant HSE
		{
			driver.findElement(By.xpath("//div[@name='Correspondant HSE']")).click();
		}
		if (site=="21") 
		{
			WebElement recherche_site = driver.findElement(By.xpath("//input[@placeholder='Select a single site']"));
			recherche_site.sendKeys("site 21");
			driver.findElement(By.xpath("/html/body/div["+x+"]/div[3]/form/div[5]/div[1]/div[2]/ul/li/div[2]/div/label")).click(); //Select Checkbox
		}
		else if (site=="france")
		{
			Thread.sleep(1000);
			driver.findElement(By.xpath("//span[contains(text(),'FRANCE')]")).click();
		}
		driver.findElement(By.xpath("//div[@class='telecommande__validation__submit']")).click();
	}
	public int randomInt() {

		Random rand = new Random(); //instance of random class
		int upperbound = 10000;
		//generate random values from 0-24
		int int_random = rand.nextInt(upperbound); 
		return int_random;
	}
	public String shortString() {
		int leftLimit = 97; // letter 'A'
		int rightLimit = 122; // letter 'Z'
		int targetStringLength = 3;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString;
	}
public void refINC5887() throws InterruptedException {

 List nbrLigne= driver.findElements(By.xpath("//tbody/tr"));
 Thread.sleep(1500);
assertEquals(nbrLigne.size(), 1);
String reference= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[2]")).getText();
String title= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[3]")).getText();
String incidentType=driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[4]")).getText();
String site=driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[5]")).getText();
String date= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[6]")).getText();
String status= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[7]")).getText();

assertEquals(reference,"INC5887" );
assertEquals(title,"QA auto Test vlp1027" );
assertEquals(incidentType,"Autre" );
assertEquals(site,"Site 21" );
assertEquals(date, "Jul 7, 2021");
assertEquals(status,"ongoing" );
}

public void refINC5932() {

	List nbrLigne= driver.findElements(By.xpath("//tbody/tr"));
	assertEquals(nbrLigne.size(), 1);
	String reference= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[2]")).getText();
	String title= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[3]")).getText();
	String incidentType=driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[4]")).getText();
	String site=driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[5]")).getText();
	String date= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[6]")).getText();
	String status= driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/main/div/div/div[2]/div[1]/div[2]/div[1]/table/tbody/tr/td[7]")).getText();

	assertEquals(reference,"INC5932" );
	assertEquals(title,"Test_VV" );
	assertEquals(incidentType,"Accident" );
	assertEquals(site,"Site 21" );
	assertEquals(date, "Jul 22, 2021");
	assertEquals(status,"draft");
}
public void resetFilters() {
	WebElement reset= driver.findElement(By.xpath("//button[@class='rol-link d-flex'][1]"));
	reset.click();
	loadingBar();
}
public void loadingBar() {
	WebElement loadingBar= driver.findElement(By.xpath("//thead/tr[1]/th[1]/div[1]/div[2]"));
	WebDriverWait wait = new WebDriverWait(driver,8);
	wait.until(ExpectedConditions.invisibilityOf(loadingBar));
}
public void headlessMode(String headless) {
	if (headless=="oui") {
		FirefoxOptions FireOptions = new FirefoxOptions();
		//FireOptions.setHeadless(true);
		FireOptions.addArguments("--headless");
		driver = new FirefoxDriver(FireOptions);
	
	   
	}
	else if (headless=="non") {ChromeOptions ChromeOptions = new ChromeOptions();
    
    driver = new FirefoxDriver();
		
	}
	
}
public String actualDate() throws ParseException {
	DateFormat dateFormat = new SimpleDateFormat("MMM dd, YYY", Locale.ENGLISH);
	String actualDate = dateFormat.format(new Date()).replaceAll("\\.", "");
	return actualDate;
}
public void closePopUp() throws InterruptedException {
	try {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/aside[1]/div[1]/div[1]/div[1]/div[2]/a[3]")).click();		
	} catch (Exception e) {
	}
	
}
}

