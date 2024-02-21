package com.api.csv.impl;

import com.api.csv.controller.response.CsvResponse;

@FunctionalInterface
public interface CsvInterface {
    CsvResponse getTheProducerWithLongestGapBetweenTwoConsecutiveAwards();
}
