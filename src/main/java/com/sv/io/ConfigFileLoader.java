package com.sv.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileLoader {
    public static String getTopicName() throws IOException {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_NAME)) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find " + Constants.CONFIG_FILE_NAME);
                return null;
            }
            prop.load(input);
            return prop.getProperty(Constants.TOPIC_NAME);
        }
    }
}
