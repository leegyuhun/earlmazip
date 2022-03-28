package aptdata.earlmazip.repository;

import aptdata.earlmazip.domain.SiteInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SiteInfoRepository {

    private final EntityManager em;

    public String findSiteInfo(String name) {
        log.info("findSiteInfo name: " + name);
//        SiteInfo siteInfo = em.createQuery("select a from SiteInfo a where a.infoName = :name", SiteInfo.class)
//                .setParameter("name", name)
//                .getSingleResult();
        List<SiteInfo> siteInfos = em.createQuery("select a from SiteInfo a where a.infoName = :name", SiteInfo.class)
                .setParameter("name", name)
                .getResultList();
        if (siteInfos.size() == 0){
            return "update: -";
        }
        else {
            SiteInfo siteInfo = siteInfos.get(0);
            return siteInfo.getInfoValue();
        }
    }
}
