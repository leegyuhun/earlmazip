package aptdata.earlmazip.service;

import aptdata.earlmazip.controller.dto.EcosDataResponseDto;
import aptdata.earlmazip.repository.EcosDataRepository;
import aptdata.earlmazip.repository.SiteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EcosDataService {

    private final EcosDataRepository ecosDataRepository;

    public List<EcosDataResponseDto> getEcosData(String statCode, String itemCode1, String itemCode2, String term) {
        return ecosDataRepository.getEcosData(statCode, itemCode1, itemCode2, term);
    }

}
