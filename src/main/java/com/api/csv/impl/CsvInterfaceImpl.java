package com.api.csv.impl;

import com.api.csv.repository.model.CSV;
import com.api.csv.repository.CsvRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class CsvInterfaceImpl implements CsvInterface {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CsvRepository csvRepository;

    @PostConstruct
    public void csvOutputFileXSSFWorkbook() throws IOException, InvalidFormatException {

        final File file = getFile();

        List<String> listStrings = new ArrayList<>();
        String listString;
        final List<Double> listIntegers = new ArrayList<>();

        List<CSV> csv = new ArrayList<>();

        // Create Workbook instance holding reference to .xlsx file

        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet ws = wb.getSheetAt(0);

        log.info("Reading excel");

        for (Row row : ws) {
            Iterator<Cell> cellIterator = row.cellIterator();
            if (cellIterator.hasNext()) {
                do {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING -> {
                            listString = cell.getStringCellValue();
                            listStrings.add(listString);
                            List<String> list = Arrays.asList("year", "title", "studios", "producers", "winner");
                            listStrings.removeAll(list);
                        }
                        case NUMERIC -> {

                            listIntegers.add(cell.getNumericCellValue());

                            listStrings = new ArrayList<>();

                            CSV build = CSV.builder().listValues(listStrings).build();

                            csv.add(build);
                        }
                    }
                } while (cellIterator.hasNext());
            }
        }

        int i = 0;

        while (i < csv.size()) {
            Double v = listIntegers.get(i);
            CSV csvFinal = csv.get(i);
            csvFinal.setYear(v);
            i++;
        }

        this.csvRepository.saveAll(csv);
    }

    @Override
    public List<CSV> getTheProducerWithLongestGapBetweenTwoConsecutiveAwards() {
        return csvRepository.findAll().stream().filter(csv -> csv.getListValues().contains("yes")).toList();
    }

    private static File getFile() {
        String filename = "moviestes.xlsx";
        Path pathToFile = Paths.get(filename);
        return pathToFile.toAbsolutePath().toFile();
    }
}
