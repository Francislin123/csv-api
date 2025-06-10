package com.api.csv.auxiliar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

@Slf4j
@Component
public class GetFile {
    public File getFile() {
        log.info("Reading the file that is in the root of the project");
        final var nameCSV = "moviestes.xlsx";
        final var pathToFile = Paths.get(nameCSV);
        return pathToFile.toAbsolutePath().toFile();
    }
}
