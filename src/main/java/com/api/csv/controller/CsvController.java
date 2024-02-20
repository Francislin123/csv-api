package com.api.csv.controller;

import com.api.csv.impl.CsvInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CsvController.URI_CLIENT)
public class CsvController {

    public static final String URI_CLIENT = "/assets/csv";

    @Autowired
    private CsvInterface csvInterface;

    @RequestMapping(value = "/statics", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTheProducerWithLongestGapBetweenTwoConsecutiveAwards() {
        return new ResponseEntity<>(csvInterface.getTheProducerWithLongestGapBetweenTwoConsecutiveAwards(), HttpStatus.OK);
    }
}
