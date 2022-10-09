package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rank_ua_sigungu")
@Getter
public class RankUaSigungu {
    @Id
    private int seq;
    private int rankGubn;
    private String sigunguCode;
    private String sigunguName;
    private String landDong;
    private String aptName;
    private int useAreaTrunc;
    private String buildYear;
    private int tradeCnt;
    private int minAmt;
    private int avgAmt;
    private int maxAmt;
    private int highestCnt;
    private float highestRate;
}
