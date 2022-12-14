package com.earlmazip.controller.dto;

import com.earlmazip.domain.*;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
public class AptPriceResponseDto {
    private String sigunguCode;
    public String getDealDate() {
        return dealDate.substring(2,4) + "." + dealDate.substring(4,6) + "." + dealDate.substring(6,8);
    }
    private String dealDate;
    private String aptName;
    private float useArea;
    public float getUseArea() {
        return (float) (Math.floor(useArea * 100)/100);
    }
    private String useAreaStr;
    public String getUseAreaStr() {
        return Float.toString(this.useArea);
    }
    private int useAreaTrunc;
    private String useAreaType;
    private int dealAmt;
    private int prevDealAmt;
    private int prevLeaseAmt;
    private String prevLeaseAmtStr;

    public String getPrevLeaseAmtStr() {
        if (this.prevLeaseAmt == 0) {
            return "-";
        } else {
            return new DecimalFormat("0.0#").format((float)this.prevLeaseAmt / 10000) + "억";
        }
    }

    private String prevLeaseDate;
    private int mostHighestAmt;
    private int mostLowestAmt;

    private String mostHighestAmtStr;
    private String mostLowestAmtStr;
    public String getMostHighestAmtStr() {
        return new DecimalFormat("0.0#").format((float)this.mostHighestAmt / 10000) + "억";
    }

    public String getMostLowestAmtStr() {
        return new DecimalFormat("0.0#").format((float)this.mostLowestAmt / 10000) + "억";
    }

    private String prevDealAmtStr;

    public String getPrevDealAmtStr() {
        return new DecimalFormat("0.0#").format((float)this.prevDealAmt / 10000) + "억";
    }

    private String prevDealDate;

    public String getPrevDealDate() {
        return prevDealDate.substring(2,4) + "." + prevDealDate.substring(4,6) + "." + prevDealDate.substring(6,8);
    }

    private int diffAmt;
    private float diffRate;
    private String dealAmtStr;
    public String getDealAmtStr() {
        return new DecimalFormat("0.0#").format((float)this.dealAmt / 10000) + "억";
    }
    private int floor;
    private String buildYear;
    private String landDong;
    private String cnclDealType;
    private String cnclDealDate;
    private String dealType;

    public String getDealType() {
        if (dealType.contains("거래")) {
            return dealType.replace("거래", "");
        } else {
            return dealType;
        }
    }

    private String dealLoc;
    private String newHighest;

    public String getNewHighest() {
        if (newHighestPrice == 0) {
            return "-";
        } else {
            return "O";
        }
    }

    private int newHighestPrice;

    public AptPriceResponseDto(AptPriceRaw entity) {
        this.sigunguCode = entity.getSigunguCode();
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.useAreaType = entity.getUseAreaType();
        this.dealAmt = entity.getDealAmt();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.landDong = entity.getLandDong();
        this.cnclDealType = entity.getCnclDealType();
        this.cnclDealDate = entity.getCnclDealDate();
        this.dealType = entity.getDealType();
        this.dealLoc = entity.getDealLoc();
        this.newHighestPrice = entity.getNewHighestPrice();
        this.prevDealAmt = entity.getPrevDealAmt();
        this.prevDealDate = entity.getPrevDealDate();
        this.prevLeaseAmt = entity.getPrevLeaseAmt();
        this.prevLeaseDate = entity.getPrevLeaseDate();
        this.mostHighestAmt = entity.getMostHighestAmt();
        this.mostLowestAmt = entity.getMostLowestAmt();
        this.diffAmt = entity.getDiffAmt();
        this.diffRate = entity.getDiffRate();
    }

    public AptPriceResponseDto(AptPriceGs entity) {
        this.sigunguCode = entity.getSigunguCode();
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.useAreaType = entity.getUseAreaType();
        this.dealAmt = entity.getDealAmt();
        this.prevDealAmt = entity.getPrevDealAmt();
        this.prevLeaseAmt = entity.getPrevLeaseAmt();
        this.prevLeaseDate = entity.getPrevLeaseDate();
        this.mostHighestAmt = entity.getMostHighestAmt();
        this.mostLowestAmt = entity.getMostLowestAmt();
        this.prevDealDate = entity.getPrevDealDate();
        this.diffAmt = entity.getDiffAmt();
        this.diffRate = entity.getDiffRate();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.landDong = entity.getLandDong();
        this.cnclDealType = entity.getCnclDealType();
        this.cnclDealDate = entity.getCnclDealDate();
        this.dealType = entity.getDealType();
        this.dealLoc = entity.getDealLoc();
        this.newHighestPrice = entity.getNewHighestPrice();
    }

    public AptPriceResponseDto(CancelDealData entity) {
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.dealAmt = entity.getDealAmt();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.landDong = entity.getLandDong();
        this.cnclDealType = entity.getCnclDealType();
        this.cnclDealDate = entity.getCnclDealDate();
        this.dealType = entity.getDealType();
        this.dealLoc = entity.getDealLoc();
        this.newHighestPrice = entity.getNewHighestPrice();
    }

    public AptPriceResponseDto(RankYear entity) {
        this.sigunguCode = entity.getSigunguCode();
        this.landDong = entity.getLandDong();
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.dealAmt = entity.getDealAmt();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.dealType = entity.getDealType();
        this.newHighestPrice = entity.getNewHighestPrice();
    }

    public AptPriceResponseDto(AptDistributionRaw entity) {
        this.sigunguCode = entity.getSigunguCode();
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.useAreaType = entity.getUseAreaType();
        this.dealAmt = entity.getDealAmt();
        this.prevDealAmt = entity.getPrevDealAmt();
        this.prevDealDate = entity.getPrevDealDate();
        this.diffAmt = entity.getDiffAmt();
        this.diffRate = entity.getDiffRate();
        this.floor = entity.getFloor();
        this.landDong = entity.getLandDong();
        this.cnclDealType = entity.getCnclDealType();
        this.cnclDealDate = entity.getCnclDealDate();
        this.dealType = entity.getDealType();
        this.dealLoc = entity.getDealLoc();
        this.newHighestPrice = entity.getNewHighestPrice();
    }
}
