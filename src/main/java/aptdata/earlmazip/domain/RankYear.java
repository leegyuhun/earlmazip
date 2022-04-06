package aptdata.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rank_year")
@Getter
public class RankYear {
    @Id
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
    public String getAptName() {
        if (this.aptName.length() > 8) {
            return this.aptName.substring(0, 8) + "..";
        } else {
            return this.aptName;
        }
    }
    private float useArea;
    private int dealAmt;
    private int floor;
    private String buildYear;
    private String dealType;
}
