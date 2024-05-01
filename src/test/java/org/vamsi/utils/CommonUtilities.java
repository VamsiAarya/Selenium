package org.vamsi.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonUtilities {
    public static Properties loadPropFile(String path) {
        Properties properties = null;
        try (InputStream fis = new FileInputStream(path)) {
            properties = new Properties();
            properties.load(fis);
        } catch (FileNotFoundException f) {
            System.err.println("File was not found in this path: " + f.getMessage() + "\n" + path);
        } catch (IOException io) {
            System.err.println("Failed in reading properties file.\n" + io.getMessage());
        }

        return properties;
    }


}
