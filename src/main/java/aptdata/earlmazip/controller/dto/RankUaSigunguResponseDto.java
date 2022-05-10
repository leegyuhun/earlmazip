package aptdata.earlmazip.controller.dto;

import aptdata.earlmazip.domain.RankUaSigungu;
import aptdata.earlmazip.domain.RankYear;
import lombok.Getter;

@Getter
public class RankUaSigunguResponseDto {
    private int rankGubn;
    private int rank;

    public void setRank(int rank) {
        this.rank = rank;
    }

    private String sigunguCode;
    private String sigunguName;
    private String landDong;
    private String aptName;
    private int useAreaTrunc;
    private String buildYear;
    private int tradeCnt;
    private int minAmt;
    private int avgAmt;
    private int maxAmt;
    private int highestCnt;
    private float highestRate;

    private String tradeUrl;

    public void setTradeUrl(String tradeUrl) {
        this.tradeUrl = tradeUrl;
    }

    public RankUaSigunguResponseDto(RankUaSigungu entity) {
        this.rankGubn = entity.getRankGubn();
        this.sigunguCode = entity.getSigunguCode();
        this.sigunguName = entity.getSigunguName();
        this.landDong = entity.getLandDong();
        this.aptName = entity.getAptName();
        this.useAreaTrunc = entity.getUseAreaTrunc();
        this.buildYear = entity.getBuildYear();
        this.tradeCnt = entity.getTradeCnt();
        this.minAmt = entity.getMinAmt();
        this.avgAmt = entity.getAvgAmt();
        this.maxAmt = entity.getMaxAmt();
        this.highestCnt = entity.getHighestCnt();
        this.highestRate = entity.getHighestRate();
    }
}
