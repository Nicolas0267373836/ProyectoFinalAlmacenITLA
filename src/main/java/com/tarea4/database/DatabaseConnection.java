package com.tarea4.database;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/* Singleton: una sola clase administra la conexión a MySQL. */
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private String url;
    private String user;
    private String password;

    private DatabaseConnection() {
        try {
            Properties properties = new Properties();
            Path archivoExterno = Path.of("database.properties");
            InputStream file = Files.exists(archivoExterno)
                    ? Files.newInputStream(archivoExterno)
                    : getClass().getResourceAsStream("/database.properties");
            if (file == null) {
                throw new IllegalStateException("No existe database.properties. Copia database.properties.example.");
            }
            properties.load(file);

            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception error) {
            throw new RuntimeException("Revisa el archivo database.properties", error);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
