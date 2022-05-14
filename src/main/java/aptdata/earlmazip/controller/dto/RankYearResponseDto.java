package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.RankYear;
import lombok.Getter;

@Getter
public class RankYearResponseDto {
    private int seq;
    private String gubnCode;
    private String gubnName;
    private String landDong;
    public String getLandDong(){
        if (this.landDong.length() > 2 && this.landDong.contains("동")) {
            return this.landDong.replace("동", "");
        } else {
            return this.landDong;
        }
    }
    private String dealYear;
    private String dealMon;
    public String getDealDate() {
        return dealDate.substring(2,4) + "." + dealDate.substring(4,6) + "." + dealDate.substring(6,8);
    }
    private String dealDate;
    private String aptName;
    private float useArea;
    private int useAreaTrunc;
    private int dealAmt;
    private int floor;
    private String buildYear;
    private String dealType;
    private int newHighestPrice;
    private String newHighest;
    public String getNewHighest() {
        if(this.newHighestPrice ==0) { return  "-"; }
        else { return "O"; }
    }

    public RankYearResponseDto(RankYear entity) {
        this.gubnCode = entity.getGubnCode();
        this.gubnName = entity.getGubnName();
        this.landDong = entity.getLandDong();
        this.dealDate = entity.getDealDate();
        this.aptName = entity.getAptName();
        this.useArea = entity.getUseArea();
        this.useArea = entity.getUseAreaTrunc();
        this.dealAmt = entity.getDealAmt();
        this.floor = entity.getFloor();
        this.buildYear = entity.getBuildYear();
        this.dealType = entity.getDealType();
        this.newHighestPrice = entity.getNewHighestPrice();
    }
}
