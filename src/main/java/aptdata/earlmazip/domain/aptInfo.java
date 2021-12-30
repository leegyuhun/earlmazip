package aptdata.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "apt_info")
public class aptInfo {
    @Id
    @GeneratedValue
    @Column(name = "apt_code")
    private String aptCode;
    @Column(name = "area_code")
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
    @Column(name = "mp_area_60")
    private int mpArea60; // 전용면적 60이하
    @Column(name = "mp_area_85")
    private int mpArea85; // 전용면적 60이상 85미만
    @Column(name = "mp_area_135")
    private int mpArea135; //전용면적 85이상 135이하
    @Column(name = "mp_area_136")
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
}
