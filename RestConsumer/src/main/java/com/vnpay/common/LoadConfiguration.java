package com.vnpay.common;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoadConfiguration {

    private static final Logger logger = LogManager.getLogger(LoadConfiguration.class);
    private static volatile PropertiesConfiguration properties;

    static {
        properties = new PropertiesConfiguration();
        try {
            String path = System.getProperty("user.dir") + MessageConfig.PATH_FILE_CONFIG;
            logger.info("Path reader {}", path);
            InputStream inputStream = new FileInputStream(path);
            if (inputStream == null) {
                throw new IOException("File not found");
            }
            properties.load(inputStream);
        } catch (Exception e) {
            logger.error("Can not load file properties: ", e);
        }
    }

    private LoadConfiguration() {
    }

    /**
     * load information from properties file
     *
     * @return Properties
     */
    public static PropertiesConfiguration getInstance() {
        return properties;
    }
}
