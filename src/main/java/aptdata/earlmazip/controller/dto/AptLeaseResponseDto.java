package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.AptLeaseRaw;
import lombok.Getter;

@Getter
public class AptLeaseResponseDto {
    private int seq;
    private String areaCode;
    private String sidoCode;
    private String sigunguCode;
    private String aptCode;
    private String dealYear;
    private String dealMon;
    private String dealDay;
    private String dealDate;
    public String getDealDate() {
        return dealDate.substring(0,4) + "-" + dealDate.substring(4,6) + "-" + dealDate.substring(6,8);
    }
    private String aptName;
    private float useArea;
    private int useAreaTrunc;
    private int deposit;
    private int monthlyRent;
    private String monthlyRentStr;
    private int floor;
    private String buildYear;
    private String landDong;
    private String jibun;
    private String regnCode;

    public AptLeaseResponseDto(AptLeaseRaw entity) {
        this.seq = entity.getSeq();
        this.areaCode = entity.getAreaCode();
        this.sidoCode = entity.getSidoCode();
        this.sigunguCode = entity.getSigunguCode();
        this.aptCode = entity.getAptCode();
        this.dealYear = entity.getDealYear();
        this.dealMon = entity.getDealMon();
        this.dealDay = entity.getDealDay();
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.deposit = entity.getDeposit();
        this.monthlyRent = entity.getMonthlyRent();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.landDong = entity.getLandDong();
        this.jibun = entity.getJibun();
        this.regnCode = entity.getRegnCode();
        if (entity.getMonthlyRent() == 0) { this.monthlyRentStr = "-"; }
        else { this.monthlyRentStr = Integer.toString(entity.getMonthlyRent()); }
    }
}
