package br.com.tqi.bootcamp.bookstore.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final String repositoryFileDirectory;

    public String persist(final MultipartFile file, final String fileName) {

        Path fileDirectory = generateFileName(repositoryFileDirectory, file, fileName);

        try {
            Path createdFile = Files.createFile(fileDirectory);
            Files.write(createdFile, file.getBytes());
            log.info("File successfully persisted: {}", fileDirectory);
        } catch (IOException ex) {
            log.error("Failed to persist file\n: {}", fileDirectory);
            throw new RuntimeException(ex);
        }

        return fileDirectory.toString();

    }

    // TODO Conversar sobre o tratamento de exceções
    public void delete(final String fileName) {
        try {
            Files.delete(Paths.get(fileName));
            log.info("File deleted successfully: {}", fileName);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", fileName);
            throw new RuntimeException(e);
        }
    }

    private Path generateFileName(String directory, MultipartFile file, String fileName) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return Paths.get(directory + "/" + fileName + "." + extension);
    }

}
