package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.AptInfo;
import lombok.Getter;

@Getter
public class AptResponseDto {
    private String aptCode;
    private String areaCode;
    private String sidoCode;
    private String sigunguCode;
    private String aptName;
    private String as1;
    private String as2;
    private String as3;
    private String aptAddr;
    private String saleType;
    private String heatType;
    private int dongCnt;
    private int daCnt;
    private String bComp; // 시공사
    private String aComp; // 시행사
    private String aptType; //단지분류
    private String aptRoadAddr; // 도로명 주소
    private String hoCnt; // 호 수
    private String useAplyDate; //사용승인일자
    private String useAplyYear; //사용승인년도
    private int mpArea60; // 전용면적 60이하
    private int mpArea85; // 전용면적 60이상 85미만
    private int mpArea135; //전용면적 85이상 135이하
    private int mpArea136; //135초과
    private float bigMpRate; //국민평수 초과비율
    public float getBigMpRate() { return bigMpRate * 100; }
    private int parkCntUp; //주차가능수(지상)
    private int parkCntDn; //주차가능수(지하)
    private float parkRate; //세대당 주차수
    private String busDist; //버스정류장거리
    private String subLine; //지하철호선
    private String subName; //지하철역명
    private String subDist; //지하철역 거리
    private String welfFclt; //부대,복리시설
    private String convFclt; //편의시설
    private String educFclt; //교육시설
    private String bjdCode; //법정동코드

    public AptResponseDto(AptInfo entity) {
        this.aptCode = entity.getAptCode();
        this.areaCode = entity.getAreaCode();
        this.sidoCode = entity.getSidoCode();
        this.sigunguCode = entity.getSigunguCode();
        this.aptName = entity.getAptName();
        this.as1 = entity.getAs1();
        this.as2 = entity.getAs2();
        this.as3 = entity.getAs3();
        this.aptAddr = entity.getAptAddr();
        this.saleType = entity.getSaleType();
        this.heatType = entity.getHeatType();
        this.dongCnt = entity.getDongCnt();
        this.daCnt = entity.getDaCnt();
        this.bComp = entity.getBComp();
        this.aComp = entity.getAComp();
        this.aptType = entity.getAptType();
        this.aptRoadAddr = entity.getAptRoadAddr();
        this.hoCnt = entity.getHoCnt();
        this.useAplyDate = entity.getUseAplyDate();
        this.useAplyYear = entity.getUseAplyYear();
        this.mpArea60 = entity.getMpArea60();
        this.mpArea85 = entity.getMpArea85();
        this.mpArea135 = entity.getMpArea135();
        this.mpArea136 = entity.getMpArea136();
        this.bigMpRate = entity.getBigMpRate();
        this.parkCntUp = entity.getParkCntUp();
        this.parkCntDn = entity.getParkCntDn();
        this.parkRate = entity.getParkRate();
        this.busDist = entity.getBusDist();
        this.subLine = entity.getSubLine();
        this.subName = entity.getSubName();
        this.subDist = entity.getSubDist();
        this.welfFclt = entity.getWelfFclt();
        this.convFclt = entity.getConvFclt();
        this.educFclt = entity.getEducFclt();
        this.bjdCode = entity.getBjdCode();
    }
}
