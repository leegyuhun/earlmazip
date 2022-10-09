package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "apt_distribution_raw")
@Getter
public class AptDistributionRaw {
    @Id
    private int seq;
    private String areaCode;
    private String sidoCode;
    private String sigunguCode;
    private String dealYear;
    private String dealMon;
    private String dealDay;
    private String dealDate;
    private String aptName;
    private float useArea;
    private int useAreaTrunc;
    private String useAreaType;
    private int dealAmt;
    private int prevDealAmt;
    private String prevDealDate;
    private int diffAmt;
    private float diffRate;
    private int floor;
    private String buildYear;
    private String landDong;
    private String jibun;
    private String regnCode;
    private String cnclDealType;
    private String cnclDealDate;
    private String dealType;
    private String dealLoc;
    private int newHighestPrice;

}
