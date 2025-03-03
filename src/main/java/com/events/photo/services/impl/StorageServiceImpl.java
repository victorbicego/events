package com.events.photo.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.events.photo.services.StorageService;

@Service
public class StorageServiceImpl implements StorageService {


    private final Path rootLocation;

    // O local de armazenamento é definido via application.yaml ou usa "uploads" como padrão.
    public StorageServiceImpl(@Value("${storage.location:uploads}") String storageLocation) {
        this.rootLocation = Paths.get(storageLocation);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório de upload", e);
        }
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        // Gera um nome único para evitar conflitos
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(filename).normalize().toAbsolutePath();

        // Verifica se o arquivo está sendo salvo dentro do diretório configurado
        if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
            throw new RuntimeException("Tentativa de armazenamento fora do diretório permitido.");
        }

        // Salva o arquivo (substituindo se já existir)
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        return destinationFile.toString();
    }
}
