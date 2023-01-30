package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rank_office")
@Getter
public class RankOffice {
    @Id
    private int seq;
    private String sigunguCode;
    private String sigunguName;
    private String landDong;
    private String dealYear;
    private String dealMon;
    private String dealDate;
    private String officeName;
    private String buildYear;
    private float useArea;
    private int useAreaTrunc;
    private String useAreaType;
    private int dealAmt;
    private int floor;
    private String dealType;
    private String dealLoc;
}
