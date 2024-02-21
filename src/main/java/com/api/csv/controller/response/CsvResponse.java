package com.api.csv.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CsvResponse {
    public List<Min> min;
    public List<Max> max;
}
