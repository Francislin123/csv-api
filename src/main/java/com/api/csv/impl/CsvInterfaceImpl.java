package com.api.csv.impl;

import com.api.csv.controller.response.CsvResponse;
import com.api.csv.controller.response.Max;
import com.api.csv.controller.response.Min;
import com.api.csv.repository.CsvRepository;
import com.api.csv.repository.model.CSV;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class CsvInterfaceImpl implements CsvInterface {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CsvRepository csvRepository;

    @PostConstruct
    public void csvOutputFileXSSFWorkbook() {
        try {

        final File file = getFile();

        List<String> listStrings = new ArrayList<>();
        String listString;
        final List<Double> listIntegers = new ArrayList<>();

        List<CSV> csvList = new ArrayList<>();

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

                            csvList.add(build);
                        }
                    }
                } while (cellIterator.hasNext());
            }
        }

        int i = 0;

        while (i < csvList.size()) {
            Double v = listIntegers.get(i);
            CSV csvFinal = csvList.get(i);
            csvFinal.setYear(v);
            i++;
        }

            log.info("Saving in repository");

        this.csvRepository.saveAll(csvList);

        } catch (IOException | InvalidFormatException e) {
            log.trace("Exception message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public CsvResponse getTheProducerWithLongestGapBetweenTwoConsecutiveAwards() {

        log.info("init to statistics");

        final List<CSV> yes =
                this.csvRepository.findAll().stream().filter(csv -> csv.getListValues().contains("yes")).toList();

        final List<CSV> duplicates = this.getDuplicates(yes);

        final List<ResultInterval> resultIntervalFindFaster = this.findFasterMin(duplicates);

        final List<ResultInterval> resultIntervalFindLargestRange = this.findLargestRangeMax(duplicates);

        final List<Min> minList = new ArrayList<>();
        final List<Max> maxList = new ArrayList<>();

        int i = 0;

        while (i < resultIntervalFindFaster.size()) {

            final ResultInterval resultIntervalParse = resultIntervalFindFaster.get(i);

            minList.add(Min.builder()
                    .producer(resultIntervalParse.producer)
                    .interval(resultIntervalParse.interval)
                    .previousWin(resultIntervalParse.previousWin)
                    .followingWin(resultIntervalParse.followingWin)
                    .build());

            i++;
        }

        int j = 0;

        while (j < resultIntervalFindLargestRange.size()) {

            final ResultInterval resultIntervalParse = resultIntervalFindLargestRange.get(j);

            maxList.add(Max.builder()
                    .producer(resultIntervalParse.producer)
                    .interval(resultIntervalParse.interval)
                    .previousWin(resultIntervalParse.previousWin)
                    .followingWin(resultIntervalParse.followingWin)
                    .build());

            j++;
        }

        log.info("Result final to statistics");

        return CsvResponse.builder().min(minList).max(maxList).build();
    }

    private File getFile() {
        log.info("Reading the file that is in the root of the project");
        final String nameCSV = "moviestes.xlsx";
        final Path pathToFile = Paths.get(nameCSV);
        return pathToFile.toAbsolutePath().toFile();
    }

    private List<CSV> getDuplicates(final List<CSV> csvList) {
        log.info("Duplicate list started");
        return csvList.stream().collect(Collectors.groupingBy(CSV::getListValues)).entrySet().stream()
                .filter(e -> e.getValue().size() > 1).flatMap(e -> e.getValue().stream()).collect(Collectors.toList());
    }

    private List<ResultInterval> findFasterMin(final List<CSV> movies) {

        log.info("Begin findFasterMin");

        final List<ResultInterval> resultInterval = new ArrayList<>();

        double smallestRangeParse = Integer.MAX_VALUE;

        for (int i = 0; i < movies.size() - 1; i++) {

            final CSV movieCurrent = movies.get(i);
            final CSV movieNext = movies.get(i + 1);

            if (movieCurrent.getListValues().get(2).equals(movieNext.getListValues().get(2))) {

                final double interval = movieNext.getYear() - movieCurrent.getYear();

                if (interval < smallestRangeParse) {
                    smallestRangeParse = interval;
                    resultInterval.clear();
                    resultInterval.add(new ResultInterval(movieCurrent.getListValues().get(2),
                            interval, movieCurrent.getYear(), movieNext.getYear()));
                } else if (interval == smallestRangeParse) {
                    resultInterval.add(new ResultInterval(movieCurrent.getListValues().get(2),
                            interval, movieCurrent.getYear(), movieNext.getYear()));
                }
            }
        }

        log.info("End findFasterMin");

        return resultInterval;
    }

    private List<ResultInterval> findLargestRangeMax(final List<CSV> movies) {

        log.info("Begin findLargestRangeMax");

        final List<ResultInterval> resultMaxInterval = new ArrayList<>();

        double smallestRangeParse = Integer.MIN_VALUE;

        for (int i = 0; i < movies.size() - 1; i++) {

            final CSV movieCurrent = movies.get(i);
            final CSV movieNext = movies.get(i + 1);

            if (movieCurrent.getListValues().get(2).equals(movieNext.getListValues().get(2))) {

                final double interval = movieNext.getYear() - movieCurrent.getYear();

                if (interval > smallestRangeParse) {
                    smallestRangeParse = interval;
                    resultMaxInterval.clear();
                    resultMaxInterval.add(new ResultInterval(movieCurrent.getListValues().get(2),
                            interval, movieCurrent.getYear(), movieNext.getYear()));
                } else if (interval == smallestRangeParse) {
                    resultMaxInterval.add(new ResultInterval(movieCurrent.getListValues().get(2),
                            interval, movieCurrent.getYear(), movieNext.getYear()));
                }
            }
        }

        log.info("End findLargestRangeMax");

        return resultMaxInterval;
    }
}
