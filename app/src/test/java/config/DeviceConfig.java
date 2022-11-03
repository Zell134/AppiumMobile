package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("file:src/test/resources/configs/device.properties")
public interface DeviceConfig extends Config {

    String deviceName();

    String platformName();

    String platformVersion();

    String appPackage();

    String appActivity();

    String remoteURL();

}
