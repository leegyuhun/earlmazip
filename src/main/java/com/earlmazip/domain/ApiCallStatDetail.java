package com.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "api_call_stat_detail")
@Getter
@Setter
public class ApiCallStatDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    private String callYear;

    private String callMonth;

    private String callDay;

    private String callDate;

    private String apiGubn;

    private String apiName;

    private String sigunguCode;

    private String sigunguName;

    private int cnt;
}
