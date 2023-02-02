package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rank_year")
@Getter
public class RankYear {
    @Id
    private int seq;
    private String useAreaType;
    private String gubnCode;
    private String sigunguCode;
    private String sigunguName;
    private String landDong;
    private String dealYear;
    private String dealMon;
    private String dealDate;
    private String aptName;
    private float useArea;
    private int useAreaTrunc;
    private int dealAmt;
    private int floor;
    private String buildYear;
    private String dealType;
    private int newHighestPrice;
}
