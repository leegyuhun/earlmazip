package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "stat_distribution")
@Getter
public class StatDistribution {
    @Id
    @GeneratedValue
    private String seq;

    private String areaCode;

    private String sigunguCode;

    private String sigunguName;

    private String dealYear;

    private int cnt;
}
