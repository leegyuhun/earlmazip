package com.earlmazip.controller.dto;

import com.earlmazip.domain.*;
import lombok.Getter;

import java.text.DecimalFormat;

@Getter
public class StatLeaseResponseDto {
    private String sidoName;
    private String dealYear;
    private String dealMon;
    private String dealYYMM;
    public String getDealYYMM() {
        return dealYYMM.substring(0,4) + "-" + dealYYMM.substring(4,6);
    }
    private String leaseType;
    private int minDeposit;
    private int avgDeposit;
    private int maxDeposit;
    private String minDepositStr;
    private String avgDepositStr;
    private String maxDepositStr;

    public String getMinDepositStr() {
        return new DecimalFormat("0.0#").format((float)this.minDeposit / 10000) + "억";
    }

    public String getAvgDepositStr() {
        return new DecimalFormat("0.0#").format((float)this.avgDeposit / 10000) + "억";
    }

    public String getMaxDepositStr() {
        return new DecimalFormat("0.0#").format((float)this.maxDeposit / 10000) + "억";
    }

    private int minMonthlyrent;
    private int avgMonthlyrent;
    private int maxMonthlyrent;

    private int totDeposit;
    private int totMonthlyrent;

    private int cnt;

    public StatLeaseResponseDto(StatSidoLease entity) {
        this.sidoName = entity.getSidoName();
        this.dealYear = entity.getDealYear();
        this.dealMon = entity.getDealMon();
        this.dealYYMM = entity.getDealYYMM();
        this.leaseType = entity.getLeaseType();
        this.minDeposit = entity.getMinDeposit();
        this.avgDeposit = entity.getAvgDeposit();
        this.maxDeposit = entity.getMaxDeposit();

        this.minMonthlyrent = entity.getMinMonthlyrent();
        this.avgMonthlyrent = entity.getAvgMonthlyrent();
        this.maxMonthlyrent = entity.getMaxMonthlyrent();
        this.cnt = entity.getCnt();
    }

    public StatLeaseResponseDto(StatSigunguLease entity) {
        this.sidoName = entity.getSigunguName();
        this.dealYear = entity.getDealYear();
        this.dealMon = entity.getDealMon();
        this.dealYYMM = entity.getDealYYMM();
        this.leaseType = entity.getLeaseType();
        this.minDeposit = entity.getMinDeposit();
        this.avgDeposit = entity.getAvgDeposit();
        this.maxDeposit = entity.getMaxDeposit();

        this.minMonthlyrent = entity.getMinMonthlyRent();
        this.avgMonthlyrent = entity.getAvgMonthlyRent();
        this.maxMonthlyrent = entity.getMaxMonthlyRent();
        this.cnt = entity.getCnt();
    }

    public StatLeaseResponseDto(StatSigunguLease84 entity) {
        this.sidoName = entity.getSigunguName();
        this.dealYear = entity.getDealYear();
        this.dealYYMM = entity.getDealYYMM();
        this.leaseType = entity.getLeaseType();
        this.minDeposit = entity.getMinDeposit();
        this.avgDeposit = entity.getAvgDeposit();
        this.maxDeposit = entity.getMaxDeposit();

        this.minMonthlyrent = entity.getMinMonthlyrent();
        this.avgMonthlyrent = entity.getAvgMonthlyrent();
        this.maxMonthlyrent = entity.getMaxMonthlyrent();
        this.cnt = entity.getCnt();

        this.totDeposit = entity.getTotDeposit();
        this.totMonthlyrent = entity.getTotMonthlyrent();
    }
}
