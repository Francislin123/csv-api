package com.api.csv.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Min {
    public String producer;
    public double interval;
    public double previousWin;
    public double followingWin;
}
