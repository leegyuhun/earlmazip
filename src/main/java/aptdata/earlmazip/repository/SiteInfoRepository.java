package aptdata.earlmazip.repository;

import aptdata.earlmazip.domain.SiteInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SiteInfoRepository {

    private final EntityManager em;

    public String findSiteInfo(String name) {
        SiteInfo siteInfo = em.createQuery("select a from SiteInfo a where a.infoName = :name", SiteInfo.class)
                .setParameter("name", name)
                .getSingleResult();
        return siteInfo.getInfoValue();
    }
}
