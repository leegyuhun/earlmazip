package com.earlmazip.repository;

import com.earlmazip.domain.IpBlock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
            em.merge(item);
            return false;
        }
    }
}
