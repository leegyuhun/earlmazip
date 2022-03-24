package aptdata.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "site_info")
@Getter
public class SiteInfo {
    @Id
    @GeneratedValue
    private int idx;

    @Column(name = "info_name")
    private String infoName;

    @Column(name = "info_value")
    private String infoValue;
}
