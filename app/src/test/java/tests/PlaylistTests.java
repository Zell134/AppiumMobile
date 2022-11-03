package tests;

import com.codeborne.selenide.Configuration;
import driver.DeviceDriver;
import org.junit.jupiter.api.BeforeAll;

public class PlaylistTests extends BaseTest{
    
    @BeforeAll
    public static void init() {
        Configuration.browser = DeviceDriver.class.getName();
    }
}
