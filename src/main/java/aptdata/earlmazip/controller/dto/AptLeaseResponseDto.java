package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.AptLeaseRaw;
import lombok.Getter;

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
    private String befMonthlyRentStr;
    private String monthlyRentStr;
    private int floor;
    private String buildYear;
    private String landDong;
//    public String getLandDong(){
//        if (this.landDong.length() > 2 && this.landDong.contains("동")) {
//            return this.landDong.replace("동", "");
//        } else {
//            return this.landDong;
//        }
//    }

    public AptLeaseResponseDto(AptLeaseRaw entity) {
        this.sigunguCode = entity.getSigunguCode();
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.useAreaType = entity.getUseAreaType();
        this.deposit = entity.getDeposit();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.landDong = entity.getLandDong();

        this.dealType = entity.getDealType();
        this.dealTerm = entity.getDealTerm();
        this.renewalUse = entity.getRenewalUse();
        this.befDeposit = entity.getBefDeposit();
        if (entity.getMonthlyRent() == 0) { this.monthlyRentStr = "-"; }
        else { this.monthlyRentStr = Integer.toString(entity.getMonthlyRent()); }
        if (entity.getBefMonthlyRent() == 0) { this.befMonthlyRentStr = "-"; }
        else { this.befMonthlyRentStr = Integer.toString(entity.getBefMonthlyRent()); }
    }
}
