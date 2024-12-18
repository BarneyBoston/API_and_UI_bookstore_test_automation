package app.bookstore.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

@Getter
public class Config {
    private static Config instance;

    private String dbUsername;
    private String dbPassword;
    private String dbConnString;
    private String baseUri;
    private String consumerKey;
    private String consumerSecret;

    public static Config getInstance() {
        if (instance == null) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            String env = System.getProperty("ENV", "local");
            String configFilePath = String.format("src/test/resources/config-%s.yaml", env);
            try {
                instance = mapper.readValue(new File(configFilePath), Config.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load configuration file: " + configFilePath, e);
            }
        }
        return instance;
    }
}

