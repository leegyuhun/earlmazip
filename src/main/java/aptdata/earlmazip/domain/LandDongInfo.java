package aptdata.earlmazip.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="land_dong_info")
@Getter
public class LandDongInfo {
    @Id
    @GeneratedValue
    private int seq;

    private String sigunguCode;
    private String sigunguName;
    private String landDong;
}
