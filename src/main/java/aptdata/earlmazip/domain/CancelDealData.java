package aptdata.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cancel_deal_data")
@Getter
public class CancelDealData {
    @Id
    private int seq;
    private String areaCode;
    private String sidoCode;
    private String sigunguCode;
    private String dealYear;
    private String dealMon;
    private String dealDay;
//    public String getDealDate() {
//        return dealDate.substring(0,4) + "-" + dealDate.substring(4,6) + "-" + dealDate.substring(6,8);
//    }
    private String dealDate;
    private String aptName;
    private float useArea;
    private int dealAmt;
    private int floor;
    private String buildYear;
    private String landDong;
    private String cnclDealType;
    private String cnclDealDate;
    private String dealType;
    private String dealLoc;
    private int newHighestPrice;

}
