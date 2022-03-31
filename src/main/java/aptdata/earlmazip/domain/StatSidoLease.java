package aptdata.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "stat_sido_lease")
@Getter
public class StatSidoLease {
    @Id
    @GeneratedValue
    @Column(name="seq")
    private String seq;

    @Column(name = "sido_code")
    private String sidoCode;

    @Column(name = "sido_name")
    private String sidoName;

    @Column(name = "deal_year")
    private String dealYear;

    @Column(name = "deal_mon")
    private String dealMon;

    @Column(name = "deal_yymm")
    private String dealYYMM;

    @Column(name = "lease_type")
    private String leaseType;

    @Column(name = "min_deposit")
    private int minDeposit;

    @Column(name = "avg_deposit")
    private int avgDeposit;

    @Column(name = "max_deposit")
    private int maxDeposit;

    @Column(name = "min_Monthlyrent")
    private int minMonthlyrent;

    @Column(name = "avg_Monthlyrent")
    private int avgMonthlyrent;

    @Column(name = "max_Monthlyrent")
    private int maxMonthlyrent;

    @Column(name = "cnt")
    private int cnt;
}
