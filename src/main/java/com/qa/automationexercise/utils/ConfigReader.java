package com.qa.automationexercise.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";
    private static final String SECRETS_PATH = "src/test/resources/secrets.properties";

    static {
        properties = new Properties();

        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties from " + CONFIG_PATH, e);
        }

        try (FileInputStream fis = new FileInputStream(SECRETS_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            // secrets.properties is gitignored and optional — silently skip
            // if it's missing (e.g. on a fresh clone or CI without it set up)
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value;
    }

    public static String getBaseUrl() { return get("baseUrl"); }
    public static int getImplicitWait() { return Integer.parseInt(get("implicitWait")); }
    public static int getExplicitWait() { return Integer.parseInt(get("explicitWait")); }
    public static String getDefaultBrowser() { return get("defaultBrowser"); }
    public static String getReqresApiKey() { return get("reqresApiKey"); }
}
