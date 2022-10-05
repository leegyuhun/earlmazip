package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.LandDongInfoDto;
import aptdata.earlmazip.domain.LandDongInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LandDongRepository extends JpaRepository<LandDongInfo, Long> {

    @Query("select a from LandDongInfo a where a.sigunguCode = :sigunguCode")
    List<LandDongInfoDto> getLandDongList_BySigunguCode(@Param("sigunguCode") String sigunguCode);

}
