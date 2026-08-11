package com.tarea4.database;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/* Singleton: una sola clase administra la conexión a MySQL. */
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private String url;
    private String user;
    private String password;
    private boolean tablasPreparadas;

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
        Connection connection = DriverManager.getConnection(url, user, password);
        try {
            prepararTablas(connection);
            return connection;
        } catch (Exception error) {
            connection.close();
            throw error;
        }
    }

    private synchronized void prepararTablas(Connection connection) throws Exception {
        if (tablasPreparadas) {
            return;
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS usuarios_20252437 ("
                    + "id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,"
                    + "usuario VARCHAR(50) NOT NULL,"
                    + "nombre VARCHAR(80) NOT NULL,"
                    + "apellido VARCHAR(80) NOT NULL,"
                    + "telefono VARCHAR(25) NOT NULL,"
                    + "correo VARCHAR(120) NOT NULL,"
                    + "password_hash VARCHAR(255) NOT NULL,"
                    + "creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (id),"
                    + "CONSTRAINT uq_usuarios_20252437_usuario UNIQUE (usuario),"
                    + "CONSTRAINT uq_usuarios_20252437_correo UNIQUE (correo)"
                    + ") ENGINE=InnoDB");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS productos_20252437 ("
                    + "id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,"
                    + "nombre VARCHAR(100) NOT NULL,"
                    + "marca VARCHAR(80) NOT NULL,"
                    + "categoria VARCHAR(80) NOT NULL,"
                    + "precio DECIMAL(10,2) NOT NULL,"
                    + "cantidad_disponible INT NOT NULL,"
                    + "creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                    + "PRIMARY KEY (id)"
                    + ") ENGINE=InnoDB");
            tablasPreparadas = true;
        }
    }
}
