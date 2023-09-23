package backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfig {
    private Properties properties = new Properties();

    public DBConfig(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDBURL() {
        return properties.getProperty("DB_URL");
    }

    public String getDBUser() {
        return properties.getProperty("DB_USER");
    }

    public String getDBPassword() {
        return properties.getProperty("DB_PASSWORD");
    }
}

