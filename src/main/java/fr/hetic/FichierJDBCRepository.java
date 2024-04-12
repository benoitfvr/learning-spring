package fr.hetic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import javax.sql.DataSource;

@Repository
public class FichierJDBCRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    @Autowired
    public FichierJDBCRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public void readFichiersFromDatabase() {
        try (Connection conn = dataSource.getConnection()) {
            String sqlFichier = "SELECT * FROM FICHIER WHERE TYPE = 'OP'";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlFichier)) {

                while (rs.next()) {
                    Fichier fichier = new Fichier();
                    fichier.setId(rs.getInt("ID"));
                    fichier.setNom(rs.getString("NOM"));
                    fichier.setType(rs.getString("TYPE"));

                    processFichierFromDB(fichier, conn);
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }

    private static void processFichierFromDB(Fichier fichier, Connection conn) {
        String outputFilePath = fichier.getNom().replace(".op", ".res");
        File outputFile = new File(outputFilePath);

        String sqlLigne = "SELECT * FROM LIGNE WHERE FICHIER_ID = " + fichier.getId();

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlLigne);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            while (rs.next()) {
                Ligne ligne = new Ligne();
                ligne.setId(rs.getInt("ID"));
                ligne.setParam1(rs.getInt("PARAM1"));
                ligne.setParam2(rs.getInt("PARAM2"));
                ligne.setOperateur(rs.getString("OPERATEUR").charAt(0));
                ligne.setFichierId(rs.getInt("FICHIER_ID"));

                try {
                    Operation operation = OperationFactory.getOperation(String.valueOf(ligne.getOperateur()));
                    int result = operation.execute(ligne.getParam1(), ligne.getParam2());
                    writer.write(String.valueOf(result));
                    writer.newLine();
                } catch (IllegalArgumentException | ArithmeticException e) {
                    writer.write("Error: " + e.getMessage());
                    writer.newLine();
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("An error occurred processing file " + fichier.getNom() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
