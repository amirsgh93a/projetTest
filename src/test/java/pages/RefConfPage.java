package pages;

import java.awt.Desktop.Action;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class RefConfPage {
public WebDriver driver;

public RefConfPage(WebDriver driver) {
	this.driver= driver;
}
By checkAEnjeux= By.id("nbIssues");
public WebElement checkAEnjeux() {
	return driver.findElement(checkAEnjeux);
}
By nonConforme= By.xpath("//input[@data-name='Non conforme']");
public WebElement nonConforme() {
	return driver.findElement(nonConforme);
}
public void chooseDomaine() {
	driver.findElement(By.id("s2id_autogen4")).click();
	Actions action= new Actions(driver);
	action.sendKeys("environnement").sendKeys(Keys.ENTER).perform();
}
By btnExporter= By.id("exportTableToXlsButton");
public WebElement btnExporter() {
	return driver.findElement(btnExporter);
}
By faireStatConformiteBtn= By.xpath("//a[@href=\"/?p=1104&sp=3\"]");
public WebElement faireStatConformiteBtn() {
	return driver.findElement(faireStatConformiteBtn);
}
By generateButton= By.id("generateButton");
public WebElement generateButton() {
	return driver.findElement(generateButton);
}
By reportName= By.xpath("//div[@class='nyroModalCont']//input[@name='reportName']");
public WebElement reportName() {
	return driver.findElement(reportName);
}
By goToDownloadReports= By.xpath("//a[@data-gtm-item-id='header_download']");
public WebElement goToDownloadReports() {
	return driver.findElement(goToDownloadReports);
}
By affichageListeConformite= By.xpath("//a[@href='/?p=1104&sp=1']");
public WebElement affichageListeConformite() {
	return driver.findElement(affichageListeConformite);
}
By addDashboard= By.xpath("//div[@class='addWidget']");
public WebElement addDashboard() {
	return driver.findElement(addDashboard);
}
By ajoutVignettes= By.xpath("(//div[@class='container-modal']//p[contains(text(),'Ajouter')])[2]");
public WebElement ajoutVignettes() {
	return driver.findElement(ajoutVignettes);
}
By ajoutTauxConformite= By.xpath("(//div[@class='container-modal']//p[contains(text(),'Ajouter')])[3]");
public WebElement ajoutTauxConformite() {
	return driver.findElement(ajoutTauxConformite);
}

}