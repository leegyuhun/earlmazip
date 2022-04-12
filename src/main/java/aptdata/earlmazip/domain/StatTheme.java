package aptdata.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "stat_theme")
@Getter
public class StatTheme {
    @Id
    @GeneratedValue
    private String seq;

    private String themeCode;

    private String themeName;

    private String date;

    public String getDate() {
        return date.substring(0,4) + "-" + date.substring(4,6);
    }

    private int minPrice;

    private int avgPrice;

    private int maxPrice;

    private int cnt;

    private int highestCnt;

    private float highestRate;
}
