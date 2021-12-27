package aptdata.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "stat_area_yymm")
@Getter
@Setter
public class stat_area_yymm {
    @Id
    @GeneratedValue
    @Column(name="SEQ")
    private String seq;

    @Column(name = "area_code")
    private String areaCode;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "deal_yymm")
    private String dealYYMM;

    @Column(name = "min_price")
    private int minPrice;

    @Column(name = "avg_price")
    private int avgPrice;

    @Column(name = "max_price")
    private int maxPrice;

    @Column(name = "cnt")
    private int cnt;
}
