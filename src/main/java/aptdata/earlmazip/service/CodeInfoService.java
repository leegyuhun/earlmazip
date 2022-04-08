package aptdata.earlmazip.service;

import aptdata.earlmazip.repository.CodeInfoRepository;
import aptdata.earlmazip.repository.SiteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeInfoService {

    private final CodeInfoRepository codeInfoRepository;

    public String getCodeName(String code) {
        return codeInfoRepository.getCodeName(code);
    }

}
