package com.api.csv.impl;

import com.api.csv.controller.response.CsvResponse;

import java.util.List;

@FunctionalInterface
public interface CsvInterface {
    List<CsvResponse> getTheProducerWithLongestGapBetweenTwoConsecutiveAwards();
}
