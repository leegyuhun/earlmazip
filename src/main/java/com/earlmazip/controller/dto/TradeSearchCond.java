package com.earlmazip.controller.dto;

import lombok.Data;

@Data
public class TradeSearchCond {

    private String sigunguCode;
    private String dealYear;
    private String dealMon;
    private String uaType;
    private String landDong;

    private int useAreaTrunc;
    private String aptName;
    
    private String leaseType; //0: 전세 1: 월세
}
