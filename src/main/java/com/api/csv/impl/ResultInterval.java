package com.api.csv.impl;

import lombok.Builder;
import lombok.Data;

@Data
public class ResultInterval {

    String producer;
    double interval;
    double previousWin;
    double followingWin;

    @Builder
    public ResultInterval(String producer, double interval, double previousWin, double followingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }
}
