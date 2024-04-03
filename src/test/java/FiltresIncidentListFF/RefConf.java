package FiltresIncidentListFF;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.RefConfPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import GlobalGets.Global;
public class RefConf extends Global{
	int total=0;
	public RefConf() throws IOException {
		super();
	}
	RefConfPage refConfPage;
	@BeforeTest
	public void authentification() throws InterruptedException {
		headlessMode("non");
		login();
		Thread.sleep(5000);
		closePopups();
		telecommande("admin", "21");
		clickRefConf();
		refConfPage = new RefConfPage(driver);	
	}
		@AfterTest
		public void tearDown() {
			driver.quit();
		}
	@Test (priority = 0)
	public void conformitesAtraiter() throws Exception {
		sousMenuRefConf("conformites a traiter");
		Select filtrerPar= new Select(driver.findElement(By.name("filter")));
		filtrerPar.selectByIndex(1);
		refConfPage.chooseDomaine();
		refConfPage.checkAEnjeux().click();
		refConfPage.nonConforme().click();
		int nombreConformiter= driver.findElements(By.xpath("//tbody//tr")).size();
		for (int i=1; i<=nombreConformiter; i++) {
			String getLastLine=driver.findElement(By.xpath("//tbody/tr["+i+"]/td[8]")).getText();
			int number=Integer.parseInt(getLastLine); 
			total+=number;
		}
		System.out.println("total des des conformites est:" +total);
		// verification conformiteAtraiter est egale a 8
		assertEquals(total, 8.0);
		refConfPage.btnExporter().click();
		Thread.sleep(2000);
		totalNombreAEnjeux();

	}
	@Test(priority=1, dependsOnMethods = {"conformitesAtraiter"})
	public void faireStatistiquesConformite() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(1000);
		sousMenuRefConf("FaireStatistiquesConformite");
		refConfPage.faireStatConformiteBtn().click();
		// check environnement
		driver.findElement(By.id("idThemeList_437")).click();
		// check non conforme
		driver.findElement(By.xpath("//label[@for='filter_conformity_status_list_3']")).click();
		// check applicable a enjeux	
		driver.findElement(By.id("filter_status[]_-1")).click();
		// check compter par
		driver.findElement(By.xpath("//label[@for='compter_par']")).click();
		// Appuyer sur le btn Generer
		refConfPage.generateButton().click();
		Thread.sleep(1000);
		refConfPage.reportName().sendKeys(nom);
		WebElement saveReport= driver.findElement(By.xpath("//input[@value='Enregistrer']"));
		saveReport.click();
		Thread.sleep(1500);
		refConfPage.goToDownloadReports().click();
		// Check si le rapport est dispo ou pas
		String disponibiliteRapport= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")).getText();
		System.out.println(disponibiliteRapport);
		do  {
			Thread.sleep(5000);
			driver.navigate().refresh();
			String reCheck= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")).getText();
			disponibiliteRapport=reCheck;
		}
		while (disponibiliteRapport.contains("Pas encore disponible"));
		WebElement downloadReport= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")); downloadReport.click();
		rapportFaireStatistiquesConformite();
	}
	@Test(priority = 2, dependsOnMethods = {"conformitesAtraiter", "faireStatistiquesConformite"})
	public void affichage_Liste_donnees_Conformite() throws Exception {
		//		driver.navigate().back();
		driver.navigate().to("https://www.test.hse-compliance.net/?p=1104&sp=0");
		refConfPage.affichageListeConformite().click();
		// check environnement
		driver.findElement(By.id("idThemeList_437")).click();
		// check non conforme
		driver.findElement(By.xpath("//label[@for='filter_conformity_status_list_3']")).click();
		// check applicable a enjeux	
		driver.findElement(By.id("filter_status[]_-1")).click();
		donneesLieesAuContenuReglementaire();
		WebElement etatConformite= driver.findElement(By.xpath("//label[@for='column_reexamination_list_column_conformity_label']"));
		etatConformite.click();
		refConfPage.generateButton().click();
		Thread.sleep(1000);
		refConfPage.reportName().sendKeys(name2);
		WebElement saveReport= driver.findElement(By.xpath("//input[@value='Enregistrer']"));
		saveReport.click();
		Thread.sleep(2000);
		refConfPage.goToDownloadReports().click();
		String disponibiliteRapport= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")).getText();
		System.out.println(disponibiliteRapport);
		do  {
			Thread.sleep(5000);
			driver.navigate().refresh();
			String reCheck= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")).getText();
			disponibiliteRapport=reCheck;
		}
		while (disponibiliteRapport.contains("Pas encore disponible"));
		WebElement downloadReport= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")); downloadReport.click();
		verifNonConforme();
	}
	@Test(priority = 3,dependsOnMethods = {"conformitesAtraiter", "faireStatistiquesConformite","affichage_Liste_donnees_Conformite"})
	public void vignette() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		clickRefConf();
		refConfPage.addDashboard().click();
		Thread.sleep(500);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", refConfPage.ajoutVignettes());
		Thread.sleep(1500);
		WebElement crayonModifDashboard=driver.findElement(By.xpath("(//button[@class='highFiveList__button'])[1]"));
		crayonModifDashboard.click();
		// Choose Domaine
		Select domaine= new Select(driver.findElement(By.xpath("//Select[@class='radiusContent__select']")));
		domaine.selectByIndex(1);
		// choose conformite
		Select conformite= new Select(driver.findElement(By.xpath("//Select[@class='radiusContent__select capitalizeText']")));
		conformite.selectByIndex(3);
		// Check A enjeux
		driver.findElement(By.xpath("//label[@class='checkboxType2 checkboxType2--text is-marginRight-10']")).click();
		// Clic Button Enregistrer
		driver.findElement(By.xpath("//button[@class='button--1']")).click();
		Thread.sleep(3000);
		// Verifier la vignette article non conforme
		String getnumberArticleNonConforme=driver.findElement(By.xpath("(//p[@class='highFiveList__title cursor-pointer'])[1]//span")).getText();
		Thread.sleep(1000);
		assertEquals(getnumberArticleNonConforme, "8");

		// Export Vignette
		WebElement menuButton= driver.findElement(By.xpath("(//button[@class='menu__button'])[1]")); menuButton.click();
		WebElement downloadInExcel= driver.findElement(By.xpath("(//ul//li[@class='menuItem'])[5]")); downloadInExcel.click();
		Thread.sleep(2000);
		refConfPage.goToDownloadReports().click();
		// download report
		String disponibiliteRapport= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")).getText();
		System.out.println(disponibiliteRapport);
		do  {
			Thread.sleep(5000);
			driver.navigate().refresh();
			String reCheck= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")).getText();
			disponibiliteRapport=reCheck;
		}
		while (disponibiliteRapport.contains("Pas encore disponible"));
		WebElement downloadReport= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")); downloadReport.click();
		Thread.sleep(1000);
		rapportVignette();
	}
	@Test(priority = 4,dependsOnMethods = {"conformitesAtraiter", "faireStatistiquesConformite","affichage_Liste_donnees_Conformite","vignette"})
	public void tauxDeConformite() throws InterruptedException, IOException {
		driver.navigate().to("https://www.test.hse-compliance.net/?p=2&sp=1");
		refConfPage.addDashboard().click();
		Thread.sleep(500);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", refConfPage.ajoutTauxConformite());
		Thread.sleep(2000);
		WebElement menuButton= driver.findElement(By.xpath("(//button[@class='menu__button'])[1]")); menuButton.click();
		WebElement modifModeCalcul= driver.findElement(By.xpath("(//ul//li[@class='menuItem'])[1]")); modifModeCalcul.click();
		Thread.sleep(1000);
		//Check tt les taux de conformite
		cocheTauxConformite();
		// Check a enjeux
		driver.findElement(By.xpath("//label[@class='checkboxType2 checkboxType2--text is-marginRight-10']")).click();
		// Clic Button Enregistrer
		driver.findElement(By.xpath("//button[@class='button--1']")).click();
		Thread.sleep(1500);
		//Check Taux de conformite Environnement
		driver.findElement(By.xpath("(//label[@class='radioType2__label'])[2]")).click();
		Thread.sleep(4000);
		// Verif 8 Articles
		String getNonConforme =driver.findElement(By.xpath("//span[contains(text(),'8 articles')]")).getText();
		assertEquals(getNonConforme, "8 articles");
		// Export Taux de conf
		menuButton.click();
		WebElement downloadInExcel= driver.findElement(By.xpath("(//ul//li[@class='menuItem'])[5]")); downloadInExcel.click();
		Thread.sleep(2000);
		refConfPage.goToDownloadReports().click();
		// download report
		String disponibiliteRapport= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")).getText();
		System.out.println(disponibiliteRapport);
		do  {
			Thread.sleep(5000);
			driver.navigate().refresh();
			String reCheck= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")).getText();
			disponibiliteRapport=reCheck;
		}
		while (disponibiliteRapport.contains("Pas encore disponible"));
		WebElement downloadReport= driver.findElement(By.xpath("//tbody/tr[1]/td[7]")); downloadReport.click();
		Thread.sleep(1000);
		rapportTauxConf();
	}
}
