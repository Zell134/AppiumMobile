package driver;

import config.ConfigReader;
import org.openqa.selenium.ScreenOrientation;

public class DriverHelper extends DeviceDriverWithoutPermissions {

    public static void goBack() {
        driver.navigate().back();
    }

    public static void unlockScreen() {
        if (driver.isDeviceLocked()) {
            driver.unlockDevice();
        }
    }

    public static void rotateScreen(ScreenOrientation orientation) {
        driver.rotate(orientation);
    }

    public static void closeApp() {
        driver.closeApp();
    }

    public static void launchApp() {
        driver.activateApp(ConfigReader.deviceConfig.appPackage());
    }
    
    public static boolean isAppRunning() {
        return driver.getPageSource().contains(ConfigReader.deviceConfig.appPackage());
    }
}
