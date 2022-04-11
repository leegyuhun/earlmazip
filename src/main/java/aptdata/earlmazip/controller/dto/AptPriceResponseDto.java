package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.AptPriceRaw;
import lombok.Getter;

@Getter
public class AptPriceResponseDto {
    public String getDealDate() {
        return dealDate.substring(2,4) + "." + dealDate.substring(4,6) + "." + dealDate.substring(6,8);
    }
    private String dealDate;
    private String aptName;
    public String getAptName() {
        if (this.aptName.length() > 8) {
            return this.aptName.substring(0, 8) + "..";
        } else {
            return this.aptName;
        }
    }
    private float useArea;
    private int useAreaTrunc;
    private int dealAmt;
    private int floor;
    private String buildYear;
    private String landDong;
    public String getLandDong(){
        if (this.landDong.length() > 2 && this.landDong.contains("동")) {
            return this.landDong.replace("동", "");
        } else {
            return this.landDong;
        }
    }
    private String cnclDealType;
    private String cnclDealDate;
    private String dealType;
    private String dealLoc;
    private String newHighest;
    private int newHighestPrice;

    public AptPriceResponseDto(AptPriceRaw entity) {
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.dealAmt = entity.getDealAmt();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.landDong = entity.getLandDong();
        this.cnclDealType = entity.getCnclDealType();
        this.cnclDealDate = entity.getCnclDealDate();
        this.dealType = entity.getDealType();
        this.dealLoc = entity.getDealLoc();
        this.newHighestPrice = entity.getNewHighestPrice();
        if (entity.getNewHighestPrice() == 0) { this.newHighest = "-"; }
        else { this.newHighest = "O"; }
    }
}
