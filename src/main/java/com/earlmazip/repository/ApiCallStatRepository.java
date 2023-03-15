package com.earlmazip.repository;

import com.earlmazip.domain.ApiCallStat;
import com.earlmazip.domain.ApiCallStatDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApiCallStatRepository {

//    EntityManager em;
//
//    public ApiCallStatRepository(EntityManager em) {
//        this.em = em;
//    }

    private final EntityManager em;

    public void WriteApiCallStatDetail(String apiName, String sigunguCode, String sigunguName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());

        ApiCallStatDetail item;

        List<ApiCallStatDetail> items = em.createQuery("select a from ApiCallStatDetail a "
                + " where a.callDate = :date and a.apiName = :name", ApiCallStatDetail.class)
                .setParameter("date", date)
                .setParameter("name", apiName)
                .getResultList();

        item = new ApiCallStatDetail();
        if (items.size() == 0) {
            item = new ApiCallStatDetail();
            item.setCallDate(date);
            item.setCallYear(date.substring(0, 4));
            item.setCallMonth(date.substring(4, 6));
            item.setCallDay(date.substring(6, 8));
            item.setApiName(apiName);
            item.setSigunguCode(sigunguCode);
            item.setSigunguName(sigunguName);
            item.setCnt(1);
            em.persist(item);
        } else {
            item = items.get(0);
            item.setCnt(item.getCnt()+1);
            em.merge(item);
        }
    }

    public void WriteApiCallStat(String gubn, String name, String code) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());

        ApiCallStat item;

        List<ApiCallStat> items = em.createQuery("select a from ApiCallStat a "
                        + " where a.callDate = :date and a.apiGubn = :gubn "
                        + " and a.apiName = :name",ApiCallStat.class)
                .setParameter("date", date)
                .setParameter("gubn", gubn)
                .setParameter("name", name)
                .getResultList();
        item = new ApiCallStat();
        if (items.size() == 0) {
            item = new ApiCallStat();
            item.setCallDate(date);
            item.setCallYear(date.substring(0, 4));
            item.setCallMonth(date.substring(4, 6));
            item.setCallDay(date.substring(6, 8));
            item.setApiGubn(gubn);
            item.setApiName(name);
            item.setCnt(1);
            em.persist(item);
        } else {
            item = items.get(0);
            item.setCnt(item.getCnt()+1);
            em.merge(item);
        }
    }

    public List<ApiCallStat> LoadTodayApiCallList(String gubn) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());
        if (gubn.equals("TOTAL")) {
            return em.createQuery("select a from ApiCallStat a "
                            + " where a.callDate = :date "
                            + "   and a.apiGubn <> 'MENU' "
                            + "   and a.apiGubn <> 'SIGUNGU' "
                            + " order by a.cnt desc", ApiCallStat.class)
                    .setParameter("date", date)
                    .getResultList();
        } else {
            return em.createQuery("select a from ApiCallStat a "
                            + " where a.callDate = :date "
                            + "   and a.apiGubn = :gubn "
                            + " order by a.cnt desc", ApiCallStat.class)
                    .setParameter("gubn", gubn)
                    .setParameter("date", date)
                    .getResultList();
        }
    }
}
