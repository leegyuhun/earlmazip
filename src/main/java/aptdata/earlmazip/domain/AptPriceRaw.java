package aptdata.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "apt_price_raw")
@Getter
public class AptPriceRaw {
    @Id
    private int seq;
    private String areaCode;
    private String sidoCode;
    private String sigunguCode;
    private String aptCode;
    private String serialNum;
    private String dealYear;
    private String dealMon;
    private String dealDay;
//    public String getDealDate() {
//        return dealDate.substring(0,4) + "-" + dealDate.substring(4,6) + "-" + dealDate.substring(6,8);
//    }
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
    private int newHighestPrice;

}
