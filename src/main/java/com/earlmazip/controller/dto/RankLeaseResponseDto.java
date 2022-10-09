package com.earlmazip.controller.dto;

import com.earlmazip.domain.RankLease;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
public class RankLeaseResponseDto {
    private String useAreaType;
    private String leaseType;
    private String gubnCode;
    private String gubnName;
    private String landDong;
    private String dealYear;
    private String dealMon;
    public String getDealDate() {
        return dealDate.substring(2,4) + "." + dealDate.substring(4,6) + "." + dealDate.substring(6,8);
    }
    private String dealDate;
    private String dealType;
    private String dealTerm;
    private String renewalUse;
    private String aptName;
    private float useArea;

    public float getUseArea() {
        return (float) (Math.floor(useArea * 100)/100);
    }

    private int useAreaTrunc;
    private int deposit;
    private String depositStr;
    private String monthlyRentStr;

    public String getDepositStr() {
        return new DecimalFormat("0.0#").format((float)this.deposit / 10000) + "억";
    }
    private int floor;
    private String buildYear;

    public RankLeaseResponseDto(RankLease entity) {
        this.useAreaType = entity.getUseAreaType();
        this.leaseType = entity.getLeaseType();
        this.gubnCode = entity.getGubnCode();
        this.gubnName = entity.getGubnName();
        this.landDong = entity.getLandDong();
        this.dealYear = entity.getDealYear();
        this.dealMon = entity.getDealMon();
        this.dealDate = entity.getDealDate();
        this.dealType = entity.getDealType();
        this.dealTerm = entity.getDealTerm();
        this.renewalUse = entity.getRenewalUse();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.deposit = entity.getDeposit();
        if (entity.getMonthlyRent() == 0) { this.monthlyRentStr = "-"; }
        else { this.monthlyRentStr = String.format("%,d", entity.getMonthlyRent()); }
//        else { this.monthlyRentStr = Integer.toString(entity.getMonthlyRent()); }
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
    }
}
