package aptdata.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rank_year")
@Getter @Setter
public class RankYear {
    @Id
    private int seq;
    private String gubnCode;
    private String gubnName;
    private String landDong;
    private String dealYear;
    private String dealMon;
    public String getDealDate() {
        return dealDate.substring(0,4) + "-" + dealDate.substring(4,6) + "-" + dealDate.substring(6,8);
    }
    private String dealDate;
    private String aptName;
    private float useArea;
    private int dealAmt;
    private int floor;
    private String buildYear;
}
