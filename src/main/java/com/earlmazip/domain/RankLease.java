package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rank_lease")
@Getter
public class RankLease {
    @Id
    private int seq;
    private String useAreaType;
    private String leaseType;
    private String gubnCode;
    private String gubnName;
    private String landDong;
    private String dealYear;
    private String dealMon;
    private String dealDate;
    private String dealType;
    private String dealTerm;
    private String renewalUse;
    private String aptName;
    private String buildYear;
    private float useArea;
    private int useAreaTrunc;
    private int deposit;
    private int monthlyRent;
    private int floor;
}
