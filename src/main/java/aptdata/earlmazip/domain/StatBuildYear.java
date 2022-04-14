package aptdata.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "stat_buildyear")
@Getter
public class StatBuildYear {
    @Id
    @GeneratedValue
    private String seq;

    private String regnCode;

    private String regnName;

    private String dealYear;

    private String dealMon;

    private String buildYear;

    @Column(name = "deal_yymm")
    private String dealYYMM;

    public String getDealYYMM() {
        return dealYYMM.substring(0,4) + "-" + dealYYMM.substring(4,6);
    }

    private int minPrice;

    private int avgPrice;

    private int maxPrice;

    private int cnt;
}
