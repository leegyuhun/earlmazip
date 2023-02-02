package com.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rank_lease_uatype")
@Getter
public class RankLeaseUatype {
    @Id
    private int seq;
    private int rankGubn;
    private int dealYear;
    private String gubnCode;
    private String sigunguCode;
    private String sigunguName;
    private String landDong;
    private String aptName;
    private float useArea;
    private int useAreaTrunc;
    private String useAreaType;
    private String buildYear;
    private int tradeCnt;
    private int minDeposit;
    private int avgDeposit;
    private int maxDeposit;
}
