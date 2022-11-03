package driver;

import config.ConfigReader;
import static driver.DeviceDriverWithoutPermissions.driver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BaseDriver {

    private static final String PLATFORM_NAME = ConfigReader.deviceConfig.platformName();
    private static final String APP_PACKAGE = ConfigReader.deviceConfig.appPackage();
    private static final String APP_ACTIVITY = ConfigReader.deviceConfig.appActivity();
    private static final String URL = ConfigReader.deviceConfig.remoteURL();

    protected void fillCapabilities(DesiredCapabilities desiredCapabilities) {
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.SKIP_UNLOCK, true);
    }

    protected WebDriver getDriverWithoutPermissions(DesiredCapabilities desiredCapabilities) {
        fillCapabilities(desiredCapabilities);
        try {
            driver = new AndroidDriver<>(new URL(URL), desiredCapabilities);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BaseDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (driver.isDeviceLocked()) {
            driver.unlockDevice();
        }
        driver.manage().timeouts().implicitlyWait(02, TimeUnit.SECONDS);
        return driver;
    }

    protected WebDriver getDriver(DesiredCapabilities desiredCapabilities) {
        fillCapabilities(desiredCapabilities);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);        

        try {
            driver = new AndroidDriver<>(new URL(URL), desiredCapabilities);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BaseDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        driver.manage().timeouts().implicitlyWait(02, TimeUnit.SECONDS);
        return driver;
    }
}
