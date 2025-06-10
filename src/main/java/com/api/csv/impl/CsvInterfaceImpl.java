package com.api.csv.impl;

import com.api.csv.auxiliar.FindFasterMin;
import com.api.csv.auxiliar.FindLargestRangeMax;
import com.api.csv.auxiliar.GetDuplicates;
import com.api.csv.controller.response.CsvResponse;
import com.api.csv.controller.response.Max;
import com.api.csv.controller.response.Min;
import com.api.csv.repository.CsvRepository;
import com.api.csv.repository.model.CSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CsvInterfaceImpl implements CsvInterface {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private FindLargestRangeMax findLargestRangeMax;

    @Autowired
    private FindFasterMin findFasterMin;

    @Autowired
    private GetDuplicates getDuplicates;

    @Autowired
    private CsvRepository csvRepository;

    @Override
    public CsvResponse getTheProducerWithLongestGapBetweenTwoConsecutiveAwards() {

        log.info("init to statistics");

        final List<CSV> yes =
                this.csvRepository.findAll().stream().filter(csv -> csv.getListValues().contains("yes")).toList();

        final List<CSV> duplicates = this.getDuplicates.getDuplicates(yes);

        final List<ResultInterval> resultIntervalFindFaster = this.findFasterMin.findFasterMin(duplicates);

        final List<ResultInterval> resultIntervalFindLargestRange = this.findLargestRangeMax.findLargestRangeMax(duplicates);

        final List<Min> minList = new ArrayList<>();
        final List<Max> maxList = new ArrayList<>();

        int i = 0;

        while (i < resultIntervalFindFaster.size()) {

            final var resultIntervalParse = resultIntervalFindFaster.get(i);

            minList.add(Min.builder().producer(
                    resultIntervalParse.producer).interval(resultIntervalParse.interval).previousWin(
                    resultIntervalParse.previousWin).followingWin(resultIntervalParse.followingWin).build());
            i++;
        }

        int j = 0;

        while (j < resultIntervalFindLargestRange.size()) {

            final var resultIntervalParse = resultIntervalFindLargestRange.get(j);

            maxList.add(Max.builder().producer(resultIntervalParse.producer).interval(
                    resultIntervalParse.interval).previousWin(
                    resultIntervalParse.previousWin).followingWin(resultIntervalParse.followingWin).build());
            j++;
        }

        log.info("Result final to statistics");

        return CsvResponse.builder().min(minList).max(maxList).build();
    }
}
