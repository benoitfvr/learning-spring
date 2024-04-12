package fr.hetic;

import fr.hetic.FichierJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearningSpringApplication implements CommandLineRunner {

    private final FichierJDBCRepository fichierJDBCRepository;

    @Autowired
    public LearningSpringApplication(FichierJDBCRepository fichierJDBCRepository) {
        this.fichierJDBCRepository = fichierJDBCRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fichierJDBCRepository.readFichiersFromDatabase();
    }
}
