package com.api.csv.auxiliar;

import com.api.csv.repository.model.CSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GetDuplicates {
    public List<CSV> getDuplicates(final List<CSV> csvList) {
        log.info("Duplicate list started");
        return csvList.stream().collect(Collectors.groupingBy(CSV::getListValues)).entrySet()
                .stream().filter(e -> e.getValue().size() > 1).flatMap(e -> e.getValue()
                        .stream()).collect(Collectors.toList());
    }
}
