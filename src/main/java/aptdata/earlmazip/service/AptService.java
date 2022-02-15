package aptdata.earlmazip.service;

import aptdata.earlmazip.controller.dto.AptResponseDto;
import aptdata.earlmazip.domain.AptInfo;
import aptdata.earlmazip.repository.AptRepository;
import aptdata.earlmazip.repository.AptSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AptService {

    private final AptRepository aptRepository;

    public List<AptInfo> findApts(){return aptRepository.findAll();}

    public List<AptInfo> findByName(String name) {
        return aptRepository.findByName(name);
    }

    public List<AptResponseDto> findAllByName(AptSearch aptSearch) {
        return aptRepository.findAllByString(aptSearch);
    }
}
