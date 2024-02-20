package com.api.csv.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Max {
    public String producer;
    public int interval;
    public int previousWin;
    public int followingWin;
}
