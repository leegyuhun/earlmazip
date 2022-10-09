package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "apt_lease_raw")
@Getter
public class AptLeaseRaw {
    @Id
    private int seq;
    private String areaCode;
    private String sidoCode;
    private String sigunguCode;
    private String aptCode;
    private String dealYear;
    private String dealMon;
    private String dealDay;
    private String dealDate;
    private String dealType;
    private String dealTerm;
    private String renewalUse;
    private String aptName;
    private float useArea;
    private int useAreaTrunc;
    private String useAreaType;
    private int befDeposit;
    private int deposit;
    private int befMonthlyRent;
    private int monthlyRent;
    private int floor;
    private String buildYear;
    private String landDong;
    private String jibun;
    private String regnCode;
}
