package com.api.csv.repository;

import com.api.csv.repository.model.CSV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvRepository extends JpaRepository<CSV, Long> {

}
