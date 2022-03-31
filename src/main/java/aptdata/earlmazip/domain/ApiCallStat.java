package aptdata.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "api_call_stat")
@Getter
@Setter
public class ApiCallStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(name = "call_year")
    private String callYear;

    @Column(name = "call_month")
    private String callMonth;

    @Column(name = "call_day")
    private String callDay;

    @Column(name = "call_date")
    private String callDate;

    @Column(name = "api_gubn")
    private String apiGubn;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "cnt")
    private int cnt;
}
