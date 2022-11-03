package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import driver.DriverHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    
    @BeforeAll
    public static void setup(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.startMaximized = false;
        Configuration.browserSize = null;
        Configuration.timeout = 10000;
    }
    
    @BeforeEach
    public void startDriver() {
        Allure.step("Open Application", (Allure.ThrowableRunnableVoid)Selenide::open);
        DriverHelper.unlockScreen();
    }
    
     @AfterEach
    public void afterEach() {
        Allure.step("Close Application", Selenide::closeWebDriver);         
    }   
    
    @AfterAll
    public static void tearDown(){
        SelenideLogger.removeAllListeners();
    }
    
}
