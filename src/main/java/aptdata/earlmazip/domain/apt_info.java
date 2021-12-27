package aptdata.earlmazip.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="apt_info")
@Getter
@Setter
public class apt_info {
    @Id
    @GeneratedValue
    @Column(name="apt_code")
    private String aptCode;
    @Column(name="area_code")
    private String areaCode;
    @Column(name="sido_code")
    private String sidoCode;
    @Column(name="sigungu_code")
    private String sigunguCode;
    @Column(name="apt_name")
    private String aptName;
    @Column(name="as1")
    private String as1;
    @Column(name="as2")
    private String as2;
    @Column(name="as3")
    private String as3;
    @Column(name="apt_addr")
    private String aptAddr;
    @Column(name="sale_type")
    private String saleType;
    @Column(name="heat_type")
    private String heatType;
    @Column(name="dong_cnt")
    private int dongCnt;
    @Column(name="da_cnt")
    private int daCnt;
    @Column(name="b_comp")
    private String bComp; // 시공사
    @Column(name="a_comp")
    private String aComp; // 시행사
    @Column(name="apt_type")
    private String aptType; //단지분류
    @Column(name="apt_road_addr")
    private String aptRoadAddr; // 도로명 주소
    @Column(name="ho_cnt")
    private String hoCnt; // 호 수
    @Column(name="use_aply_date")
    private String useAplyDate; //사용승인일자
    @Column(name="use_aply_year")
    private String useAplyYear; //사용승인년도
    @Column(name="mp_area_60")
    private int mpArea60; // 전용면적 60이하
    @Column(name="mp_area_85")
    private int mpArea85; // 전용면적 60이상 85미만
    @Column(name="mp_area_135")
    private int mpArea135; //전용면적 85이상 135이하
    @Column(name="mp_area_136")
    private int mpArea136; //135초과
    @Column(name="big_mp_rate")
    private float bigMpRate; //국민평수 초과비율
    @Column(name="park_cnt_up")
    private int parkCntUp; //주차가능수(지상)
    @Column(name="park_cnt_dn")
    private int parkCntDn; //주차가능수(지하)
    @Column(name="park_rate")
    private float parkRate; //세대당 주차수
    @Column(name="bus_dist")
    private String busDist; //버스정류장거리
    @Column(name="sub_line")
    private String subLine; //지하철호선
    @Column(name="sub_name")
    private String subName; //지하철역명
    @Column(name="sub_dist")
    private String subDist; //지하철역 거리
    @Column(name="welf_fclt")
    private String welfFclt; //부대,복리시설
    @Column(name="conv_fclt")
    private String convFclt; //편의시설
    @Column(name="educ_fclt")
    private String educFclt; //교육시설
    @Column(name="bjd_code")
    private String bjdCode; //법정동코드
}
