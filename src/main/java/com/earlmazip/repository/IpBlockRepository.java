package com.earlmazip.repository;

import com.earlmazip.domain.IpBlock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IpBlockRepository {

    private final EntityManager em;

    public Boolean IsBlockIP(String ipAddress) {
        List<IpBlock> items = em.createQuery("select a from IpBlock a "
                                                + " where a.ipAddress = :ipAddress", IpBlock.class)
                .setParameter("ipAddress", ipAddress)
                .getResultList();
        IpBlock item = new IpBlock();
        if (items.size() == 0) {
            return true;
        } else {
            item = items.get(0);
            item.setBlockCnt(item.getBlockCnt()+1);
            item.setUdt(new Date());
            em.merge(item);
            return false;
        }
    }

    public Boolean AddBlockIP(String ipAddress) {
        List<IpBlock> items = em.createQuery("select a from IpBlock a "
                        + " where a.ipAddress = :ipAddress", IpBlock.class)
                .setParameter("ipAddress", ipAddress)
                .getResultList();
        IpBlock item = new IpBlock();
        if (items.size() == 0) {
            item.setIpAddress(ipAddress);
            em.persist(item);
            return true;
        } else {
            return false;
        }
    }

    public List<IpBlock> GetIPBlock() {
        return em.createQuery("select a from IpBlock a "
                        + " where a.blockCnt > 0 "
                        + " order by a.blockCnt desc", IpBlock.class)
                .getResultList();
    }

}
