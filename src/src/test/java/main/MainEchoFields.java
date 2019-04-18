package main;

import com.dev10.connection.ConnectionConfig;
import com.dev10.dao.enums.ConnectionDescriptor;
import com.dev10.dao.generate.SourceUtilsDao;

import java.sql.Connection;

public class MainEchoFields {

    public static void main(String[] args) throws Throwable {

        System.out.println("\r\nDémarrage de echoFields...\r\n");

        ConnectionConfig connectionConfig = new ConnectionConfig(
                ConnectionDescriptor.POSTGRESQL,
                "localhost",
                "5432",
                "workshop_tests_ci",
                "postgres",
                "postgres"
        );

        try (Connection con = connectionConfig.getConnexion()) {
            new SourceUtilsDao("nope").echoFields(con, "public", "t_echeance", true);
        }

    }

}