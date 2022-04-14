package aptdata.earlmazip.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "ecos_data")
@Getter
public class EcosData {
    @Id
    @GeneratedValue
    private int seq;

    private String statCode;
    private String statName;

    private String itemCode1;
    private String itemName1;

    private String year;
    private String date;
    private String dataValue;
    private String unitName;
}