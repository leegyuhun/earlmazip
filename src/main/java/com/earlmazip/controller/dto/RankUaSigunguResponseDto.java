package com.earlmazip.controller.dto;

import com.earlmazip.domain.RankLeaseUatype;
import com.earlmazip.domain.RankUaSigungu;
import com.earlmazip.domain.RankUatypeSigungu;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
public class RankUaSigunguResponseDto {
    private int rankGubn;
    private int rank;

    public void setRank(int rank) {
        this.rank = rank;
    }

    private int dealYear;
    private String gubnCode;
    private String sigunguCode;
    private String sigunguName;
    private String landDong;
    private String aptName;
    private float useArea;
    private String useAreaStr;
    public String getUseAreaStr() {
        return Float.toString(this.useArea);
    }

    private int useAreaTrunc;
    private String useAreaType;

    private String buildYear;
    private int tradeCnt;

    private int minAmt;
    private String minAmtStr;
    public String getMinAmtStr() {
        return new DecimalFormat("#.00").format((float)this.minAmt / 10000) + " 억";
    }
    private int avgAmt;
    private String avgAmtStr;
    public String getAvgAmtStr() {
        return new DecimalFormat("#.00").format((float)this.avgAmt / 10000) + " 억";
    }
    private int maxAmt;
    private String maxAmtStr;
    public String getMaxAmtStr() {
        return new DecimalFormat("#.00").format((float)this.maxAmt / 10000) + " 억";
    }

    private int minDeposit;
    private String minDepositStr;
    public String getMinDepositStr() {
        return new DecimalFormat("#.00").format((float)this.minDeposit / 10000) + " 억";
    }
    private int avgDeposit;
    private String avgDepositStr;
    public String getAvgDepositStr() {
        return new DecimalFormat("#.00").format((float)this.avgDeposit / 10000) + " 억";
    }
    private int maxDeposit;
    private String maxDepositStr;
    public String getMaxDepositStr() {
        return new DecimalFormat("#.00").format((float)this.maxDeposit / 10000) + " 억";
    }

    private int highestCnt;
    private float highestRate;

    private String tradeUrl;

    public void setTradeUrl(String tradeUrl) {
        this.tradeUrl = tradeUrl;
    }

    private String tradeUrl2;

    public void setTradeUrl2(String tradeUrl2) {
        this.tradeUrl2 = tradeUrl2;
    }

    public RankUaSigunguResponseDto(RankUaSigungu entity) {
        this.rankGubn = entity.getRankGubn();
        this.sigunguCode = entity.getSigunguCode();
        this.sigunguName = entity.getSigunguName();
        this.landDong = entity.getLandDong();
        this.aptName = entity.getAptName();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.buildYear = entity.getBuildYear();
        this.tradeCnt = entity.getTradeCnt();
        this.minAmt = entity.getMinAmt();
        this.avgAmt = entity.getAvgAmt();
        this.maxAmt = entity.getMaxAmt();
        this.highestCnt = entity.getHighestCnt();
        this.highestRate = entity.getHighestRate();
    }

    public RankUaSigunguResponseDto(RankUatypeSigungu entity) {
        this.rankGubn = entity.getRankGubn();
        this.dealYear = entity.getDealYear();
        this.gubnCode = entity.getGubnCode();
        this.sigunguCode = entity.getSigunguCode();
        this.sigunguName = entity.getSigunguName();
        this.landDong = entity.getLandDong();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.useAreaType = entity.getUseAreaType();
        this.buildYear = entity.getBuildYear();
        this.tradeCnt = entity.getTradeCnt();
        this.minAmt = entity.getMinAmt();
        this.avgAmt = entity.getAvgAmt();
        this.maxAmt = entity.getMaxAmt();
        this.highestCnt = entity.getHighestCnt();
        this.highestRate = entity.getHighestRate();
    }

    public RankUaSigunguResponseDto(RankLeaseUatype entity) {
        this.rankGubn = entity.getRankGubn();
        this.dealYear = entity.getDealYear();
        this.gubnCode = entity.getGubnCode();
        this.sigunguCode = entity.getSigunguCode();
        this.sigunguName = entity.getSigunguName();
        this.landDong = entity.getLandDong();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.useAreaType = entity.getUseAreaType();
        this.buildYear = entity.getBuildYear();
        this.tradeCnt = entity.getTradeCnt();
        this.minDeposit = entity.getMinDeposit();
        this.avgDeposit = entity.getAvgDeposit();
        this.maxDeposit = entity.getMaxDeposit();
    }
}
