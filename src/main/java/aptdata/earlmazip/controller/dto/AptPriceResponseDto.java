package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.AptPriceRaw;
import lombok.Getter;

@Getter
public class AptPriceResponseDto {
    private int seq;
    private String areaCode;
    private String sidoCode;
    private String sigunguCode;
    private String aptCode;
    private String serialNum;
    private String dealYear;
    private String dealMon;
    private String dealDay;
    public String getDealDate() {
        return dealDate.substring(0,4) + "-" + dealDate.substring(4,6) + "-" + dealDate.substring(6,8);
    }
    private String dealDate;
    private String aptName;
    private float useArea;
    private int useAreaTrunc;
    private int dealAmt;
    private int floor;
    private String buildYear;
    private String roadName;
    private String roadNameCode;
    private String roadNameBonbun;
    private String roadNameBubun;
    private String roadNameSigungu;
    private String roadNameSeq;
    private String landDong;
    private String landCode;
    private String landBonbun;
    private String landBubun;
    private String landSigungu;
    private String jibun;
    private String regnCode;
    private String cnclDealType;
    private String cnclDealDate;
    private String dealType;
    private String dealLoc;
    private String newHighest;
    private int newHighestPrice;

    public AptPriceResponseDto(AptPriceRaw entity) {
        this.seq = entity.getSeq();
        this.areaCode = entity.getAreaCode();
        this.sidoCode = entity.getSidoCode();
        this.sigunguCode = entity.getSigunguCode();
        this.aptCode = entity.getAptCode();
        this.serialNum = entity.getSerialNum();
        this.dealYear = entity.getDealYear();
        this.dealMon = entity.getDealMon();
        this.dealDay = entity.getDealDay();
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.dealAmt = entity.getDealAmt();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.roadName = entity.getRoadName();
        this.roadNameCode = entity.getRoadNameCode();
        this.roadNameBonbun = entity.getRoadNameBonbun();
        this.roadNameBubun = entity.getRoadNameBubun();
        this.roadNameSigungu = entity.getRoadNameSigungu();
        this.roadNameSeq = entity.getRoadNameSeq();
        this.landDong = entity.getLandDong();
        this.landCode = entity.getLandCode();
        this.landBonbun = entity.getLandBonbun();
        this.landBubun = entity.getLandBubun();
        this.landSigungu = entity.getLandSigungu();
        this.jibun = entity.getJibun();
        this.regnCode = entity.getRegnCode();
        this.cnclDealType = entity.getCnclDealType();
        this.cnclDealDate = entity.getCnclDealDate();
        this.dealType = entity.getDealType();
        this.dealLoc = entity.getDealLoc();
        this.newHighestPrice = entity.getNewHighestPrice();
        if (entity.getNewHighestPrice() == 0) { this.newHighest = "O"; }
        else { this.newHighest = "-"; }
    }
}
