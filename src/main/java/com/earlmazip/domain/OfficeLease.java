package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "office_lease")
@Getter
public class OfficeLease {
    @Id
    private int seq;
    private String areaCode;
    private String sidoCode;
    private String sigunguCode;
    private String dealYear;
    private String dealMon;
    private String dealDay;
    private String dealType;
    private String dealTerm;
    private String renewalUse;
    private String dealDate;
    private String officeName;
    private float useArea;
    private int useAreaTrunc;
    private String useAreaType;
    private int deposit;
    private int befDeposit;
    private int monthlyRent;
    private int befMonthlyRent;
    private int floor;
    private String buildYear;
    private String landDong;
    private String jibun;
    private String regnCode;
}
