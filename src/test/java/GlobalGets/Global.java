package GlobalGets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

import FiltresIncidentListFF.RefConf;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Global {
	public double total=0;
	public ChromeDriver driver;
	public String nom=randomString();
	public String name2= randomString();
	int compteurNonconforme=0;
	public static Properties prop;
	public Global() throws IOException {
		prop= new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\amir.sghaier\\Desktop\\workspace\\IncidentManagementRefonte\\src\\test\\java\\config\\config.properties");
		prop.load(fis);
	}
	/*
	Create a new project in the Replit IDE:

	Open the Replit website and log in to your account.
	Click on the blue "+" icon to create a new repl.
	Select the language and environment you want to use for your project.
	Upload your project files:

	Click on the "Files" tab on the left sidebar of the Replit IDE.
	Click on the "Upload file" button and select the files you want to upload from your desktop.
	Once the files are uploaded, you will see them listed in the file explorer on the left.
	Edit and run your project:

	Click on the file you want to work on in the file explorer to open it in the editor.
	Make any necessary changes to the code.
	Run your code by clicking the "Run" button at the top of the editor.
	By following these steps, you can upload and work on your own project in the Replit IDE. Let me know if you need any further assistance!

	*/






	public void login() throws InterruptedException {
		driver.manage().window().maximize();
		driver.get(prop.getProperty("URL"));
		driver.findElement(By.id("txtUserName")).sendKeys(prop.getProperty("mail"));
		driver.findElement(By.id("txtUserPass")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.id("meConnecter")).click();
	}

	public void clickRefConf() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		Thread.sleep(1000);
		WebElement menuBurger= driver.findElement(By.id("jqMenuIcon"));
		menuBurger.click();
		WebElement refConf= driver.findElement(By.id("referentielConformite")); refConf.click();
		String expectedUrl="https://www.test.hse-compliance.net/?p=2&sp=1";
		assertEquals(expectedUrl,driver.getCurrentUrl());
	}
	public void sousMenuRefConf(String menu) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement clickSousMeu=	driver.findElement(By.id("jqToggleSubCategory"));

		clickSousMeu.click();
		if (menu=="conformites a traiter") {
			WebElement gestionConf=driver.findElement(By.xpath("(//a[@class='jqNameCategory buttonType1 ribbonSubCategory__link jqDisabledLink'])[3]"));
			gestionConf.click();
			WebElement conformitesATraiter= driver.findElement(By.xpath("//a[contains(text(),'Conformités à traiter')]"));
			conformitesATraiter.click();
			Thread.sleep(3000);
			assertEquals(driver.getCurrentUrl(), "https://www.test.hse-compliance.net/?p=2&sp=7");
		}
		else if (menu=="FaireStatistiquesConformite") {
			driver.navigate().to("https://www.test.hse-compliance.net/?p=1104&sp=0");
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
	public void telecommande( String profil , String site) throws InterruptedException {

		WebElement openDashboard=driver.findElement(By.xpath("/html/body/div[2]/div[2]/button"));
		openDashboard.click();

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
			WebElement recherche_site = driver.findElement(By.xpath("//input[@placeholder='Rechercher un site']"));
			recherche_site.sendKeys("site 21");
			Thread.sleep(1000);
			driver.findElement(By.xpath("(//label[@class='checkboxType1__label'])[2]")).click();
			//			driver.findElement(By.xpath("/html/body/div["+x+"]/div[3]/form/div[5]/div[1]/div[2]/ul/li/div[2]/div/label")).click(); //Select Checkbox
		}
		else if (site=="france")
		{
			Thread.sleep(1000);
			driver.findElement(By.xpath("//span[contains(text(),'FRANCE')]")).click();
		}
		driver.findElement(By.xpath("//div[@class='telecommande__validation__submit']")).click();
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



	public void headlessMode(String headless) {
		if (headless=="oui") {
			WebDriverManager.chromedriver().setup();
			ChromeOptions ChromeOptions = new ChromeOptions();
			ChromeOptions.setHeadless(true); //ou bien options.addArguments("--headless");
			//create chrome instance
			driver = new ChromeDriver(ChromeOptions);
		}



		else if (headless=="non") {
			WebDriverManager.chromedriver().setup();
			ChromeOptions ChromeOptions = new ChromeOptions();
			driver = new ChromeDriver();

		}

	}



	public void closePopups() {
		try {
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='wm-shoutout-1000015190']//div[@class='wm-close-button walkme-x-button']")));
			WebElement ClosePopupOne=driver.findElement(By.xpath("//div[@id='wm-shoutout-1000015190']//div[@class='wm-close-button walkme-x-button']"));
			ClosePopupOne.click();
			WebElement ClosePopUpDeux=driver.findElement(By.xpath("//div[@id='wm-shoutout-1000014811']//div[@class='wm-close-button walkme-x-button']"));
			ClosePopUpDeux.click();
		} catch (Exception e) {
			System.out.println("aa");
			}
		}


	public void totalNombreAEnjeux() throws Exception {
		Workbook workbook = new Workbook("C:\\Users\\amir.sghaier\\Downloads\\Conformité(s) à traiter.xls");
		workbook.save("amir.xlsx", SaveFormat.XLSX);
		// JAVA
		FileInputStream fis=new FileInputStream(new File("C:\\Users\\amir.sghaier\\Desktop\\workspace\\RefConf\\amir.xlsx"));
		// Apache POI
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		Sheet sheet=wb.getSheetAt(0);

		int LastColumn=(sheet.getRow(1).getLastCellNum())-1;
		//System.out.println(sheet.getLastRowNum());
		//System.out.println(sheet.getPhysicalNumberOfRows());
		int rowLength=sheet.getLastRowNum();
		for(int i=1;i<=rowLength;i++) {
			total+=sheet.getRow(i).getCell(LastColumn).getNumericCellValue();
		}
		System.out.println(total);
		assertEquals(total, 8.0);
		wb.close();
	}
	public void rapportFaireStatistiquesConformite() throws IOException, InterruptedException {
		String z= nom+".xlsx";
		Thread.sleep(2000);
		FileInputStream fis=new FileInputStream(new File("C:\\Users\\amir.sghaier\\Downloads\\"+z));
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		Sheet sheet=wb.getSheetAt(0);
		int rowLength=sheet.getLastRowNum();

		int LastColumn=(sheet.getRow(8).getLastCellNum())-1;
		double total2= sheet.getRow(rowLength).getCell(LastColumn).getNumericCellValue();
		System.out.println(total2);
		assertEquals(total2, 8.0);
	}
	public void donneesLieesAuContenuReglementaire() {
		// check domaine
		driver.findElement(By.xpath("//label[@for='column_conformity_content_list_column_domaine']")).click();
		Actions action= new Actions(driver);
		action.sendKeys(Keys.TAB).sendKeys(Keys.ESCAPE).sendKeys(Keys.TAB).sendKeys(Keys.ESCAPE).sendKeys(Keys.TAB).sendKeys(Keys.ESCAPE).sendKeys(Keys.TAB).sendKeys(Keys.ESCAPE).perform();
	}
public void verifNonConforme() throws Exception {
	String z=name2+".xlsx";
	Thread.sleep(3000);
		FileInputStream fis=new FileInputStream(new File("C:\\Users\\amir.sghaier\\Downloads\\"+z));
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		Sheet sheet=wb.getSheetAt(0);
		int LastColumn=(sheet.getRow(1).getLastCellNum())-1;
		int rowLength=sheet.getLastRowNum();
		for(int i=6;i<=rowLength;i++) {
			String verifNonConforme=sheet.getRow(i).getCell(1).toString();
			Boolean VerifNonconformeText= verifNonConforme.contains("Non conforme");
			assertTrue(VerifNonconformeText);
			compteurNonconforme++;
		}
		assertEquals(compteurNonconforme, 8);
		wb.close();
	}
	public void rapportVignette() throws IOException, InterruptedException {
		String reportName= driver.findElement(By.xpath("//tbody//tr[1]//td[2]")).getText();
		String z= reportName+".xlsx";
				Thread.sleep(2000);
				FileInputStream fis=new FileInputStream(new File("C:\\Users\\amir.sghaier\\Downloads\\"+z));
				XSSFWorkbook wb=new XSSFWorkbook(fis);
				Sheet sheet=wb.getSheetAt(0);
				int rowLength=sheet.getLastRowNum();
				int firstColumn=(sheet.getRow(6).getFirstCellNum());
				String aaa =  sheet.getRow(rowLength).getCell(firstColumn).toString();
				int number=Integer.parseInt(aaa);
				System.out.println(number);
				assertEquals(number,8);
	}
	public void cocheTauxConformite() throws InterruptedException {
		int nombreTaux= driver.findElements(By.xpath("//div[@class='filter__checkbox is-marginTop-10']")).size();
		for (int i = 1; i <=nombreTaux ; i++) {
			boolean display=driver.findElement(By.xpath("(//div[@class='filter__checkbox is-marginTop-10'])["+i+"]")).isDisplayed();
			if (display) {
				driver.findElement(By.xpath("(//div[@class='filter__checkbox is-marginTop-10'])["+i+"]")).click();
			Thread.sleep(500);
			}
			}
	}
	public void rapportTauxConf() throws IOException, InterruptedException {
		String reportName= driver.findElement(By.xpath("//tbody//tr[1]//td[2]")).getText();
		String z= reportName+".xlsx";
				Thread.sleep(2000);
				FileInputStream fis=new FileInputStream(new File("C:\\Users\\amir.sghaier\\Downloads\\"+z));
				XSSFWorkbook wb=new XSSFWorkbook(fis);
				Sheet sheet=wb.getSheetAt(0);
				int rowLength=sheet.getLastRowNum();
				String aaa =  sheet.getRow(rowLength).getCell(4).toString();
				int number=Integer.parseInt(aaa);
				System.out.println(number);
				assertEquals(number,8);
	}
}

