package com.job;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import static org.testng.Assert.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class JobApplications {
	WebDriver driver;
	String firstName;
	String lastName;
	int gender;
	String dateOfBirth;
	String email;
	String phoneNumber;
	String city;
	String state;
	String country;
	int annualSalary;
	List<String> technologies;
	int yearsOfExperience;
	String education;
	String github;
	List<String> certifications;
	String additionalSkills;
	String actualIp;
	String appId;
	List<String> bodyList = new ArrayList<String>();
	List<String> emailList = new ArrayList<String>();
	Faker data = new Faker();
	Random random = new Random();

	List<String> cities;
	List<String> countries;
	Set<String> citiesSet;
	Set<String> countriesSet;
	int lineCount;

	@BeforeClass // runs once for all tests
	public void setUp() {
		System.out.println("Setting up WebDriver in BeforeClass...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// driver.manage().window().fullscreen();
	}

	@BeforeMethod // runs before each @Test
	public void navigateToHomePage() {
		System.out.println("Navigating to homepage in @BeforeMethod....");
		driver.get(
				"https://forms.zohopublic.com/murodil/form/JobApplicationForm/formperma/kOqgtfkv1dMJ4Df6k4_mekBNfNLIconAHvfdIk3CJSQ");
		firstName = data.name().firstName();
		lastName = data.name().lastName();
		gender = data.number().numberBetween(1, 3);
		dateOfBirth = data.date().birthday().toString();
		email = "acakici72@gmail.com";
		phoneNumber = data.phoneNumber().cellPhone().replace(".", "");
		city = data.address().cityName();
		state = data.address().stateAbbr();
		country = data.address().country();
		annualSalary = data.number().numberBetween(60000, 150000);
		technologies = new ArrayList<String>();
		technologies.add("Java-" + data.number().numberBetween(1, 4));
		technologies.add("HTML-" + data.number().numberBetween(1, 4));
		technologies.add("Selenium WebDriver-" + data.number().numberBetween(1, 4));
		technologies.add("TestNG-" + data.number().numberBetween(1, 4));
		technologies.add("Git-" + data.number().numberBetween(1, 4));
		technologies.add("Maven-" + data.number().numberBetween(1, 4));
		technologies.add("JUnit-" + data.number().numberBetween(1, 4));
		technologies.add("Cucumber-" + data.number().numberBetween(1, 4));
		technologies.add("API Automation-" + data.number().numberBetween(1, 4));
		technologies.add("JDBC-" + data.number().numberBetween(1, 4));
		technologies.add("SQL-" + data.number().numberBetween(1, 4));

		yearsOfExperience = data.number().numberBetween(0, 11);
		education = data.number().numberBetween(1, 4) + "";
		github = "https://github.com/CybertekSchool/selenium-maven-automation.git";
		certifications = new ArrayList<String>();
		certifications.add("Java OCA");
		certifications.add("AWS");
		certifications.add("Scrum Master");
		additionalSkills = data.job().keySkills();

	}

	@Test(priority = 2)
	public void verifyIpAddress() throws InterruptedException {

		driver.get("http://whatismyip.host/");
		String expectedIP = driver.findElement(By.xpath("//div[@id='ipv4']//preceding-sibling::p[@class='ipaddress']"))
				.getText();
		System.out.println(expectedIP);
		Assert.assertEquals(actualIp, expectedIP);

		driver.get("http://mail.google.com");

		Thread.sleep(2000);

		driver.findElement(By.id("identifierId")).sendKeys("acakici72@gmail.com" + Keys.ENTER);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[.='Next']")).click();
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("yolcu3128234" + Keys.ENTER);

		// driver.findElement(By.xpath("//input[@type='password']")).sendKeys("yolcu3128234"+Keys.ENTER);

		String application = "SDET Job Application #" + appId;
		driver.findElement(By.xpath("//input[@id='gbqfq']")).sendKeys(application + "" + Keys.ENTER);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@email='training@cybertekschool.com'][@name='training@cybertekschool..']"))
				.click();

	}

	@Test(priority = 1)
	public void submitFullApplication() {
		driver.findElement(By.xpath("//input[@name='Name_First']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='Name_Last']")).sendKeys(lastName);
		setGender(gender);
		setDateOfBirth(dateOfBirth);
		driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='countrycode']")).sendKeys(phoneNumber);
		driver.findElement(By.xpath("//input[@name='Address_City']")).sendKeys(city);
		driver.findElement(By.xpath("//input[@name='Address_Region']")).sendKeys(state);
		Select countryElem = new Select(driver.findElement(By.xpath("//select[@id='Address_Country']")));
		countryElem.selectByIndex(data.number().numberBetween(1, countryElem.getOptions().size()));
		driver.findElement(By.xpath("//input[@name='Number']")).sendKeys(String.valueOf(annualSalary) + Keys.TAB);
		verifySalaryCalculations(annualSalary);
		driver.findElement(By.xpath("//em[.=' Next ']")).click();

		// SECOND PAGE ACTIONS
		setSkillset(technologies);

		if (yearsOfExperience >= 0) {
			driver.findElement(By.xpath("//a[@rating_value='" + yearsOfExperience + "']")).click();
		}
		Select educationList = new Select(driver.findElement(By.xpath("//select[@name='Dropdown']")));
		educationList.selectByIndex(data.number().numberBetween(1, educationList.getOptions().size()));

		driver.findElement(By.xpath("//input[@name='Website']")).sendKeys(github);

		driver.findElement(By.xpath("//input[@type='text' and @name='Website']")).sendKeys(github);
		certification();
		driver.findElement(By.name("MultiLine")).sendKeys("good time management");
		driver.findElement(By.xpath("//em[.='Apply']")).click();

		actualIp = driver.findElement(By.xpath("//label[@class='descFld']//div[contains (text() ,'IP')] ")).getText()
				.substring(12);
		appId = driver.findElement(By.xpath("//label[@class='descFld']//div[contains(text(), 'Application ID:')]"))
				.getText().substring(16);

		// driver.findElement(By.id("gbqfq")).sendKeys("");

		List<String> bodyList = new ArrayList<String>();
		bodyList.add(
		driver.findElement(By.xpath("//div[contains(text(),'Dear')]")).getText().replace(",", "").substring(5));
		//bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Gender')]")).getText().substring(7));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Date of birth')]")).getText().substring(12));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Email')]")).getText().substring(4));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Phone')]")).getText().substring(4));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Address')]")).getText().substring(6));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Annual Salary')]")).getText().substring(12));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Technologies')]")).getText().substring(11));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'WebDriver')]")).getText().substring(8));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Automation')]")).getText().substring(9));
		bodyList.add(
				driver.findElement(By.xpath("//div[contains(text(),'Years of Experience')]")).getText().substring(18));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Education')]")).getText().substring(8));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'GitHub')]")).getText().substring(5));
		bodyList.add(
				driver.findElement(By.xpath("//div[contains(text(),'automation.githttps')]")).getText().substring(18));
		bodyList.add(driver.findElement(By.xpath("//div[contains(text(),'Certifications')]")).getText().substring(13));
		bodyList.add(
				driver.findElement(By.xpath("//div[contains(text(),'Additional Skills')]")).getText().substring(16));
		System.out.println(bodyList);

	}

	@Test
	public void verify() {

		List<String> emailList = new ArrayList<String>();
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Gender')]")).getText().substring(7));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Date of birth')]")).getText().substring(12));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Email')]")).getText().substring(4));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Phone')]")).getText().substring(4));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Address')]")).getText().substring(6));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Annual Salary')]")).getText().substring(12));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Technologies')]")).getText().substring(11));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'WebDriver')]")).getText().substring(8));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Automation')]")).getText().substring(9));
		emailList.add(
				driver.findElement(By.xpath("//div[contains(text(),'Years of Experience')]")).getText().substring(18));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Education')]")).getText().substring(8));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'GitHub')]")).getText().substring(5));
		emailList.add(
				driver.findElement(By.xpath("//div[contains(text(),'automation.githttps')]")).getText().substring(18));
		emailList.add(driver.findElement(By.xpath("//div[contains(text(),'Certifications')]")).getText().substring(13));
		emailList.add(
				driver.findElement(By.xpath("//div[contains(text(),'Additional Skills')]")).getText().substring(16));

		System.out.println(emailList);

		List<WebElement> jobApplication = driver.findElements(By.xpath("//span[@class='bog']"));
		for (int i = 0; i < jobApplication.size(); i++) {
			if (jobApplication.get(i).getText().contains("#150"))
				;
			System.out.println(jobApplication.get(i).getText());
			jobApplication.get(i).click();
			break;
		}

	}

	public void certification() {
		int num = data.number().numberBetween(0, 3); // how many will be selected
		int num1 = 3;
		// data.number().numberBetween(0,4); // which ones will be selected
		WebElement cert1 = driver.findElement(By.id("Checkbox_1"));
		WebElement cert2 = driver.findElement(By.id("Checkbox_2"));
		WebElement cert3 = driver.findElement(By.id("Checkbox_3"));
		List<WebElement> certList = new ArrayList<WebElement>();
		certList.add(cert1);
		certList.add(cert2);
		certList.add(cert3);
		switch (num1) {
		case 0:
			break;
		case 1:
			certList.get(num).click();
			break;
		case 2:
			certList.get(num).click();
			certList.get(num).click();
			break;
		case 3:
			certList.get(0).click();
			certList.get(1).click();
			certList.get(2).click();
			break;

		}
	}

	public void setSkillset(List<String> tech) {

		for (String skill : tech) {
			String technology = skill.substring(0, skill.length() - 2);
			int rate = Integer.parseInt(skill.substring(skill.length() - 1));

			String level = "";

			switch (rate) {
			case 1:
				level = "Expert";
				break;
			case 2:
				level = "Proficient";
				break;
			case 3:
				level = "Beginner";
				break;
			default:
				fail(rate + " is not a valid level");
			}

			String xpath = "//input[@rowvalue='" + technology + "' and @columnvalue='" + level + "']";
			driver.findElement(By.xpath(xpath)).click();

		}

	}

	public void verifySalaryCalculations(int annual) {
		String monthly = driver.findElement(By.xpath("//input[@name='Formula']")).getAttribute("value");
		String weekly = driver.findElement(By.xpath("//input[@name='Formula1']")).getAttribute("value");
		String hourly = driver.findElement(By.xpath("//input[@name='Formula2']")).getAttribute("value");

		System.out.println(monthly);
		System.out.println(weekly);
		System.out.println(hourly);

		DecimalFormat formatter = new DecimalFormat("#.##");

		assertEquals(Double.parseDouble(monthly), Double.parseDouble(formatter.format((double) annual / 12.0)));
		assertEquals(Double.parseDouble(weekly), Double.parseDouble(formatter.format((double) annual / 52.0)));
		assertEquals(Double.parseDouble(hourly), Double.parseDouble(formatter.format((double) annual / 52.0 / 40.0)));
	}

	public void setDateOfBirth(String bday) {
		String[] pieces = bday.split(" ");
		String birthDay = pieces[2] + "-" + pieces[1] + "-" + pieces[5];
		driver.findElement(By.xpath("//input[@id='Date-date']")).sendKeys(birthDay);
	}

	public void setGender(int n) {
		if (n == 1) {
			driver.findElement(By.xpath("//input[@value='Male']")).click();
		} else {
			driver.findElement(By.xpath("//input[@value='Female']")).click();
		}
	}

	@Test
	public void fullNameEmptyTest() {
		// firstly assert that you are on the correct page
		assertEquals(driver.getTitle(), "SDET Job Application");

		driver.findElement(By.xpath("//input[@elname='first']")).clear();
		driver.findElement(By.xpath("//*[@elname='last']")).clear();

		driver.findElement(By.xpath("//em[.=' Next ']")).click();

		String nameError = driver.findElement(By.xpath("//p[@id='error-Name']")).getText();
		assertEquals(nameError, "Enter a value for this field.");
	}

	public void loadSets() throws IOException {

		FileReader reader = new FileReader("C:/Users/Cakici/Downloads/MOCK_DATA.csv");
		BufferedReader breader = new BufferedReader(reader);

		String temp = breader.readLine();

		temp = breader.readLine();

		String[] something = new String[2];

		while (temp != null) {

			something = temp.split(",");
			citiesSet.add(something[0]);
			countriesSet.add(something[1]);
			lineCount++;
			temp = breader.readLine();
		}

		reader.close();
		breader.close();
	}

	public int removeDuplicates(List<String> myList) {

		Iterator<String> myIterator = myList.iterator();
		List<String> localList = new ArrayList<String>();

		String each = new String();
		boolean add = true;

		while (myIterator.hasNext()) {
			each = myIterator.next();
			add = true;

			for (String string : localList) {
				if (each.equals(string)) {
					add = false;
				}
			}

			if (add) {
				localList.add(each);
			}

		}
		return localList.size();
	}

}
