package com.api.csv.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class CsvResponse {
    public ArrayList<Min> min;
    public ArrayList<Max> max;
}
