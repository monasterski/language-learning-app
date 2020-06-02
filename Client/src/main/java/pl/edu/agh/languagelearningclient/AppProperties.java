package pl.edu.agh.languagelearningclient;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    public static String SERVER_URL;

    static {
        try (InputStream propFile = new FileInputStream("src/main/resources/application.properties")) {
            Properties appProps = new Properties();
            appProps.load(propFile);
            SERVER_URL = appProps.getProperty("app.serverURL");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
