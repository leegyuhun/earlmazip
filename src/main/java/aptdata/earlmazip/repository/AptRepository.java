package aptdata.earlmazip.repository;

import aptdata.earlmazip.domain.apt_info;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AptRepository {

    private final EntityManager em;

    public List<apt_info> findAllByString(AptSearch aptSearch) {
        String jpql = "select o from apt_info o";
        boolean isFirstCondition = true;

        if (StringUtils.hasText(aptSearch.getAptName())) {
            if (isFirstCondition) {
                jpql += " where ";
                isFirstCondition = false;
            } else{
                jpql += " and";
            }
            jpql += " o.aptName like :aptName";
        }
        TypedQuery<apt_info> query = em.createQuery(jpql, apt_info.class)
                .setMaxResults(100);
        if (StringUtils.hasText(aptSearch.getAptName())) {
            query = query.setParameter("aptName", '%' + aptSearch.getAptName() + '%');
        }
        return query.getResultList();
    }

    public List<apt_info> findAll(){
        return em.createQuery("select a from apt_info a where a.aptName = :aptName", apt_info.class)
                .setParameter("aptName", "래미안안양메가트리아")
                .getResultList();
    }

    public List<apt_info> findByName(String name){
        return em.createQuery("select a from apt_info a where a.aptName = :aptName", apt_info.class)
                .setParameter("aptName", name)
                .getResultList();
    }
}
