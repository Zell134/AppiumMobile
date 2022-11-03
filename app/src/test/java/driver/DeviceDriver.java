package driver;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DeviceDriver extends BaseDriver implements WebDriverProvider {
    
    protected static AndroidDriver driver;    
    
    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        return getDriver(desiredCapabilities);
    }
    
}
