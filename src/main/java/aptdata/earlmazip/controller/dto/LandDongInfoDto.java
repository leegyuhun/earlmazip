package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.LandDongInfo;
import lombok.Getter;

@Getter
public class LandDongInfoDto {

    private String sigunguCode;

    private String sigunguName;

    private String landDong;

    public LandDongInfoDto(LandDongInfo entity) {
        this.sigunguCode = entity.getSigunguCode();
        this.sigunguName = entity.getSigunguName();
        this.landDong = entity.getLandDong();
    }
}
