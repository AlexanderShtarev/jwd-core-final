package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertyReaderUtil {
    private static final Properties properties = new Properties();
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReaderUtil.class);

    private PropertyReaderUtil() {
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static String loadProperties(String property) {
        String toReturn = null;
        final String propertiesFileName = "src/main/resources/application.properties";
        try {
            properties.load(new FileInputStream(propertiesFileName));
            toReturn = properties.getProperty(property);
        } catch (IOException e) {
            System.out.println("File Properties non exist");
            LOGGER.error("File Properties non exist");
        }
        return toReturn;
    }
}