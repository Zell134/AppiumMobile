package config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigReader {

    public static final DeviceConfig deviceConfig = ConfigFactory.create(DeviceConfig.class);
}
