package com.earlmazip.repository;

import com.earlmazip.domain.IpCount;
import com.earlmazip.domain.QIpCount;
import com.earlmazip.domain.QPopulation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.earlmazip.domain.QIpCount.ipCount;

@Repository
public class IpCountRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QPopulation qPopulation = QPopulation.population;

    public IpCountRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

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

    public void IpInfoUpdate(IpCount pIpCount) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());

        IpCount result = queryFactory.selectFrom(ipCount)
                .where(ipCount.countDate.eq(date), ipCount.ipAddress.eq(pIpCount.getIpAddress()))
                .fetchFirst();

        if (result != null) {
            result.setCountryCode(pIpCount.getCountryCode());
            result.setIspAddr(pIpCount.getIspAddr());
            result.setIspName(pIpCount.getIspName());
            result.setUserName(pIpCount.getUserName());
            result.setUserAddr(pIpCount.getUserAddr());
            em.merge(result);
        } else {
            result = new IpCount();
            result.setCountYear(date.substring(0,4));
            result.setCountMonth(date.substring(4,6));
            result.setCountDay(date.substring(6,8));
            result.setCountDate(date);
            result.setIpAddress(pIpCount.getIpAddress());
            result.setCountryCode(pIpCount.getCountryCode());
            result.setIspAddr(pIpCount.getIspAddr());
            result.setIspName(pIpCount.getIspName());
            result.setUserName(pIpCount.getUserName());
            result.setUserAddr(pIpCount.getUserAddr());
            em.merge(result);
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
