package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.EcosData;
import aptdata.earlmazip.domain.RankYear;
import lombok.Getter;

@Getter
public class EcosDataResponseDto {

    private String statCode;
    private String statName;

    private String itemCode1;
    private String itemName1;

    private String date;
    private String dataValue;
    private String unitName;

    public EcosDataResponseDto(EcosData entity) {
        this.statCode = entity.getStatCode();
        this.statName = entity.getStatName();
        this.itemCode1 = entity.getItemCode1();
        this.itemName1 = entity.getItemName1();
        this.date = entity.getDate();
        this.dataValue = entity.getDataValue();
        this.unitName = entity.getUnitName();
    }
}
