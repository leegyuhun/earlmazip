package com.earlmazip.controller.dto;

import com.earlmazip.domain.AptLeaseRaw;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
public class AptLeaseResponseDto {
    private String sigunguCode;
    private String dealType;
    private String dealTerm;
    private String renewalUse;
    private String dealDate;
    public String getDealDate() {
        return dealDate.substring(2,4) + "." + dealDate.substring(4,6) + "." + dealDate.substring(6,8);
    }
    private String aptName;
    private float useArea;
    public float getUseArea() {
        return (float) (Math.floor(useArea * 100)/100);
    }
    private int useAreaTrunc;
    private String useAreaType;
    private int befDeposit;
    private int deposit;
    private String depositStr;

    public String getDepositStr() {
        if (this.deposit < 10000) {
            if (this.deposit < 1000) {
                return Integer.toString(this.deposit);
            } else {
                return new DecimalFormat("#,###").format((float)this.deposit);
            }
        } else {
            return new DecimalFormat("0.0#").format((float)this.deposit / 10000) + "억";
        }
    }

    private int monthlyRent;
    private String monthlyRentStr;
    public String getMonthlyRentStr() {
        if (this.monthlyRent == 0) {
            return "-";
        } else {
            return Integer.toString(this.monthlyRent);
        }
    }
    private int befMonthlyRent;
    private String befMonthlyRentStr;
    public String getBefMonthlyRentStr() {
        if (this.befMonthlyRent == 0) {
            return "-";
        } else {
            return Integer.toString(this.befMonthlyRent);
        }
    }
    private int floor;
    private String buildYear;
    private String landDong;

    public AptLeaseResponseDto(AptLeaseRaw entity) {
        this.sigunguCode = entity.getSigunguCode();
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.useAreaType = entity.getUseAreaType();
        this.deposit = entity.getDeposit();
        this.monthlyRent = entity.getMonthlyRent();
        this.befMonthlyRent = entity.getBefMonthlyRent();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.landDong = entity.getLandDong();
        this.dealType = entity.getDealType();
        this.dealTerm = entity.getDealTerm();
        this.renewalUse = entity.getRenewalUse();
        this.befDeposit = entity.getBefDeposit();
    }
}
