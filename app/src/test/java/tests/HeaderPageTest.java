package tests;

import com.codeborne.selenide.Configuration;
import driver.DeviceDriver;
import driver.DriverHelper;
import static io.qameta.allure.Allure.step;
import io.qameta.allure.Description;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pageObject.HeaderPage;
import pageObject.MainMenuPage;

public class HeaderPageTest extends BaseTest{
    
    @BeforeAll
    public static void init() {
        Configuration.browser = DeviceDriver.class.getName();
    }
    
    @Description("Application is closed via toolbar \"Exit\" button")
    @Test
    public void appClosedViaToolbarButton(){
        HeaderPage header = new HeaderPage();
        step("Apllication is running", ()-> assertThat(DriverHelper.isAppRunning()).isTrue());
        header.exitBtnClick();
        step("Application has been closed", ()-> assertThat(DriverHelper.isAppRunning()).isFalse());
    }
    
    @Description("Menu is opened via toolbar button")
    @Test
    public void menuOpenedViaToolbarButton(){
        HeaderPage header = new HeaderPage();        
        header.menuBtnClick();
        MainMenuPage menu = new MainMenuPage();
        menu.isMenuShown();
    }
}
