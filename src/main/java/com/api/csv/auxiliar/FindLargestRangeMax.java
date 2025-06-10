package com.api.csv.auxiliar;

import com.api.csv.impl.ResultInterval;
import com.api.csv.repository.model.CSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FindLargestRangeMax {

    public List<ResultInterval> findLargestRangeMax(final List<CSV> movies) {

        log.info("Begin findLargestRangeMax");

        final List<ResultInterval> resultMaxInterval = new ArrayList<>();

        double smallestRangeParse = Integer.MIN_VALUE;

        for (int i = 0; i < movies.size() - 1; i++) {

            final CSV movieCurrent = movies.get(i);
            final CSV movieNext = movies.get(i + 1);

            if (movieCurrent.getListValues().get(2).equals(movieNext.getListValues().get(2))) {

                final var interval = movieNext.getYear() - movieCurrent.getYear();

                if (interval > smallestRangeParse) {
                    smallestRangeParse = interval;
                    resultMaxInterval.clear();
                    resultMaxInterval.add(new ResultInterval(
                            movieCurrent.getListValues().get(2), interval, movieCurrent.getYear(), movieNext.getYear()));
                } else if (interval == smallestRangeParse) {
                    resultMaxInterval.add(new ResultInterval(
                            movieCurrent.getListValues().get(2), interval, movieCurrent.getYear(), movieNext.getYear()));
                }
            }
        }

        log.info("End findLargestRangeMax");

        return resultMaxInterval;
    }
}
