package br.com.tqi.bootcamp.bookstore.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FileDirectoryConfiguration {

    @Bean
    public String repositoryFileDirectory() {
        // TODO Definir um novo local para diretorio de imagens?
        Path directory = Paths.get("/tmp/book_store/images");

        try {
            if (Files.notExists(directory)) {
                log.info("Create file repository directory: {}", directory);
                return Files.createDirectory(directory).toString();
            } else {
                log.info("File repository directory already exists: {}", directory);
                return directory.toString();
            }
        } catch (IOException ex) {
            log.error("Failed to create file repository directory");
            throw new RuntimeException(ex);
        }
    }

}
