package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "stat_sigungu_lease")
@Getter
public class StatSigunguLease {
    @Id
    @GeneratedValue
    @Column(name="seq")
    private String seq;

    private String areaCode;

    private String sigunguCode;

    private String sigunguName;

    private String dealYear;

    private String dealMon;

    @Column(name = "deal_yymm")
    private String dealYYMM;

    private String leaseType;

    private String useAreaType;

    private int minDeposit;

    private int avgDeposit;

    private int maxDeposit;

    private int minMonthlyRent;

    private int avgMonthlyRent;

    private int maxMonthlyRent;

    private int cnt;
}
