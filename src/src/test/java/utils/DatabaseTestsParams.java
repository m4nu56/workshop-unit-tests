package utils;

import com.dev10.connection.ConnectionConfig;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseTestsParams {

    public static final String DATABASE_TEST_NAME = "workshop_tests_ci";

    /**
     * @return
     */
    public static ConnectionConfig getConnectionConfig() {

        Properties databaseProperties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream resourceStream = loader.getResourceAsStream("database.properties")) {
            databaseProperties.load(resourceStream);
        } catch (Exception e) {
            String errorMessage = "Erreur lors de la récupération du fichier database.properties";
            throw new RuntimeException(errorMessage + ": " + e.getMessage());
        }

        return new ConnectionConfig(
                databaseProperties.getProperty("junit.dbHost"),
                databaseProperties.getProperty("junit.dbPort"),
                databaseProperties.getProperty("junit.dbName"),
                databaseProperties.getProperty("junit.dbUser"),
                databaseProperties.getProperty("junit.dbPassword"),
                "public"
        );

    }

    /**
     * @param connectionConfig
     * @param dbBase
     * @return
     * @throws Exception
     */
    public static Connection getConnection(ConnectionConfig connectionConfig, String dbBase, String dbSchema) throws ClassNotFoundException, SQLException {

        StringBuilder buf = new StringBuilder();
        buf.append("jdbc:postgresql://").append(connectionConfig.getDbHost()).append(":").append(connectionConfig.getDbPort());

        buf.append("/");
        if (StringUtils.isNotBlank(dbBase))
            buf.append(dbBase);
        else
            buf.append(connectionConfig.getDbName());

        buf.append("?currentSchema=");
        if (StringUtils.isNotBlank(dbSchema))
            buf.append(dbSchema);
        else
            buf.append(connectionConfig.getDbSchema());


        buf.append("&useUnicode=true")
                .append("&characterEncoding=utf8")
                .append("&user=").append(connectionConfig.getDbUser())
                .append("&password=").append(connectionConfig.getDbPassword())
        ;

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(buf.toString());
    }

}
