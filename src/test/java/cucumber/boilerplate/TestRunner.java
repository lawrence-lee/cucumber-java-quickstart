/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package cucumber.boilerplate;

import com.liferay.poshi.runner.util.PropsUtil;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.boilerplate.driver.ProjectSelenium;
import cucumber.boilerplate.util.ProjectSeleniumUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@CucumberOptions(
	features = "src/test/resources/features",
	format = {
		"pretty",
		"json:src/test/build/reports/json/cucumber-report.json"
	},
	glue = {
		"cucumber.boilerplate.steps"
	},
	tags = {"~@blocked", "~@prototype"}
)
@RunWith(Cucumber.class)
public class TestRunner {

	@BeforeClass //Runs once before all tests
	public static void setUpClass() {
		//Where to store webdriver executables
		System.setProperty("wdm.targetPath", "src/test/resources/webdriver");

		//Which type of webdriver to use
		WebDriverManager chromedriver = WebDriverManager.chromedriver();
		chromedriver.setup();

		//Set the initial URL for the driver
		PropsUtil.set("portal.url", "http://localhost:8080");

		//Set WebDriver properties in Liferay PropsValues
		PropsUtil.set("browser.type", "chrome");
		PropsUtil.set(
			"selenium.chrome.driver.executable",
			System.getProperty("webdriver.chrome.driver"));
		PropsUtil.set("selenium.executable.dir.name", "");

		//Time to explicitly wait while finding elements
		PropsUtil.set("timeout.explicit.wait", "60");
	}

	@AfterClass //Runs once after all tests
	public static void tearDownClass() {
		ProjectSelenium projectSelenium = ProjectSeleniumUtil.getProjectSelenium();

		projectSelenium.close();
		projectSelenium.quit();
	}

}