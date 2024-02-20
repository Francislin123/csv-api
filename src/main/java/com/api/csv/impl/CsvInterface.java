package com.api.csv.impl;

import com.api.csv.repository.model.CSV;

import java.util.List;

@FunctionalInterface
public interface CsvInterface {
    List<CSV> getTheProducerWithLongestGapBetweenTwoConsecutiveAwards();
}
