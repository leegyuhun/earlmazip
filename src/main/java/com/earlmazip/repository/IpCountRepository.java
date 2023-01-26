package com.earlmazip.repository;

import com.earlmazip.domain.IpCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IpCountRepository {

    private final EntityManager em;

    public void IpCounting(String ipAddress) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());

        List<IpCount> items = em.createQuery("select a from IpCount a "
                                                + " where a.countDate = :date and a.ipAddress = :ipAddress", IpCount.class)
                .setParameter("date", date)
                .setParameter("ipAddress", ipAddress)
                .getResultList();
        IpCount item = new IpCount();
        if (items.size() == 0) {
            item.setCountDate(date);
            item.setCountYear(date.substring(0, 4));
            item.setCountMonth(date.substring(4, 6));
            item.setCountDay(date.substring(6, 8));
            item.setIpAddress(ipAddress);
            item.setCnt(1);
            em.persist(item);
        } else {
            item = items.get(0);
            item.setCnt(item.getCnt()+1);
            em.merge(item);
        }
    }

    public List<IpCount> GetIpHistory() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());

        return em.createQuery("select a from IpCount a "
                        + " where a.countDate = :date"
                        + " order by a.cnt desc", IpCount.class)
                .setParameter("date", date)
                .getResultList();
    }
}
