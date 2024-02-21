package com.api.csv.repository.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Entity
@Table(name = "tbl_csv")
public class CSV {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "col_year")
    private Double year;

    @Column(name = "col_list_values")
    private List<String> listValues;

    @Column(name = "col_wins")
    private Boolean wins;

    @Tolerate
    public CSV() {
        // Method default for hibernate
    }

    @Builder
    public CSV(Long id, Double year,List<String> listValues, Boolean wins) {
        this.id = id;
        this.year = year;
        this.listValues = listValues;
        this.wins = wins;
    }
}
