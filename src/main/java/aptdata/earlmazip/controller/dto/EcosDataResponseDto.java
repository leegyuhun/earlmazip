package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.EcosData;
import aptdata.earlmazip.domain.RankYear;
import lombok.Getter;

@Getter
public class EcosDataResponseDto {

    private String statCode;
    private String statName;

    private String itemCode1;
    private String itemCode2;
    private String itemName1;

    private String date;
    public String getDate() {
        return date.substring(0, 4) + "-" + date.substring(4, 6);
    }
    private String dataValue;
    private float value;
    private String unitName;

    public EcosDataResponseDto(EcosData entity) {
        this.statCode = entity.getStatCode();
        this.statName = entity.getStatName();
        this.itemCode1 = entity.getItemCode1();
        this.itemCode2 = entity.getItemCode2();
        this.itemName1 = entity.getItemName1();
        this.date = entity.getDate();
        this.dataValue = entity.getDataValue();
        this.unitName = entity.getUnitName();
        this.value = Float.parseFloat(this.dataValue) / 1000;
    }
}
