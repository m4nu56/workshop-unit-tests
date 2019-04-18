package utils;

import com.dev10.connection.ConnectionConfig;
import com.dev10.dao.ExecuteurDeScriptDao;
import com.dev10.exception.GenericRuntimeException;
import hthurow.tomcatjndi.TomcatJNDI;

import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static utils.DatabaseTestsParams.DATABASE_TEST_NAME;
import static utils.DatabaseTestsParams.getConnection;
import static utils.DatabaseTestsParams.getConnectionConfig;

public class DatabaseTestsHelper {

    public static void setUpBeforeClass() {
        // création de la base de test
        try {
            createDatabase();
        } catch (SQLException | ClassNotFoundException | IOException | NamingException e) {
            e.printStackTrace();
            throw new GenericRuntimeException("Erreur création de la base de donnée de test: " + e.getMessage());
        }

        // Chargement du pool uniquement s'il n'est pas déjà démarré
        startConnectionJdbcPoolIfNotAlreadyStarted();
    }


    private static void createDatabase() throws SQLException, ClassNotFoundException, IOException, NamingException {

        // Get connection to the database host without any db selected
        createDatabaseIfNotExists(getConnectionConfig(), DATABASE_TEST_NAME);

        // Important, on se connecte à la nouvelle base de test qu'on vient de créer
        try (Connection con = getConnectionConfig().getConnexion()) {
            con.setAutoCommit(false);

            // On vérifie si le schema existe déjà, si c'est le cas on ne fait rien
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT to_regclass('public.t_contrat');\n");
            if (rs.next()) {
                if (rs.getObject(1) == null) {

                    System.out.println("1. CREATION DE LA STRUCTURE DU SCHEMA PUBLIC");
                    new ExecuteurDeScriptDao("")
                            .executeQueryFromSqlPath(con, "scripts/create_schema_public.sql");

                } else {
                    emptyBaseTest(con); // on vide les données des précédents tests
                }
            }

            con.commit();

        }

    }

    /**
     * Vide les données de tests de la base de test
     *
     * @param con
     * @throws SQLException
     */
    public static void emptyBaseTest(Connection con) throws SQLException {
        try (PreparedStatement psmt = con.prepareStatement(
                "DELETE FROM t_echeance;" +
                        "DELETE FROM t_contrat;"
        )
        ) {
            psmt.executeUpdate();
        }
    }

    /**
     * Méthode qui vérifie si la base de donnée existe et qui la crée seulement si elle n'existe pas
     *
     * @param connectionConfig
     * @param databaseTestName
     * @throws SQLException
     */
    private static void createDatabaseIfNotExists(ConnectionConfig connectionConfig, String databaseTestName) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection(getConnectionConfig(), "postgres", "public");
             Statement statement = con.createStatement()) {
            // Create database only if not exists
            statement.executeUpdate(
                    "DO\n" +
                            "$do$\n" +
                            "DECLARE\n" +
                            "  _db TEXT := '" + databaseTestName + "';\n" +
                            "  _user TEXT := '" + connectionConfig.getDbUser() + "';\n" +
                            "  _password TEXT := '" + connectionConfig.getDbPassword() + "';\n" +
                            "BEGIN\n" +
                            "  CREATE EXTENSION IF NOT EXISTS dblink; -- enable extension \n" +
                            "  IF EXISTS (SELECT 1 FROM pg_database WHERE datname = _db) THEN\n" +
                            "    RAISE NOTICE 'Database already exists';\n" +
                            "  ELSE\n" +
                            "    PERFORM dblink_connect('host=" + connectionConfig.getDbHost() + " port=" + connectionConfig.getDbPort() + " user=' || _user || ' password=' || _password || ' dbname=' || current_database());\n" +
                            "    PERFORM dblink_exec('CREATE DATABASE ' || _db);\n" +
                            "  END IF;\n" +
                            "END\n" +
                            "$do$\n"
            );
        }
    }

    /**
     * Démarrage du pool de connection JDBC de Tomcat seulement s'il n'est pas déjà démarré.
     */
    private static void startConnectionJdbcPoolIfNotAlreadyStarted() {
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            ctx.lookup("java:comp/env");
        } catch (Exception e) {
            TomcatJNDI tomcatJNDI = new TomcatJNDI();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            tomcatJNDI.processContextXml(new File(Objects.requireNonNull(loader.getResource("contextTest.xml")).getFile()));
            tomcatJNDI.start();
        }
    }

}
