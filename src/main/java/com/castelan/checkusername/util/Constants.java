/*
 * Constants.java
 * Created on March 2017
 */
package com.castelan.checkusername.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads constants from a file
 * @author Juan Castelan
 */
public class Constants {

    private static final String PROP_FILENAME = "config.properties";
    private static final Properties prop = new Properties();
     private static class Loader {
         static Constants INSTANCE = new Constants();
     }

     private Constants () {
        InputStream inputStream;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILENAME);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + PROP_FILENAME + "' not found in the classpath");
            }
        } catch(IOException ex) {
            Logger.getLogger(Constants.class.toString()).log(Level.SEVERE , "Error al abrir el archivo de propiedades");
        }
     }
     
     public String getString(String key)  {
        return prop.getProperty(key);
     }
     
     public int getInteger(String key) {
        return Integer.valueOf(prop.getProperty(key)); 
     }

     public static Constants getInstance() {
         return Loader.INSTANCE;
     }
}
