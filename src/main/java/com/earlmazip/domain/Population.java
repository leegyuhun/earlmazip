package com.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "population")
@Getter
@Setter
public class Population {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    private String year;

    private String quarter;

    private String sigunguCode;

    private String sigunguName;

    private int households;

    private int koreanCount;

    private int foreignerCount;

    private int totalCount;

    private int oldCount;

}
