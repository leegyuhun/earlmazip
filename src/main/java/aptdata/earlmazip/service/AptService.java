package aptdata.earlmazip.service;

import aptdata.earlmazip.domain.apt_info;
import aptdata.earlmazip.repository.AptRepository;
import aptdata.earlmazip.repository.AptSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AptService {

    private final AptRepository aptRepository;

    public List<apt_info> findApts(){return aptRepository.findAll();}

    public List<apt_info> findByName(String name) {
        return aptRepository.findByName(name);
    }

    public List<apt_info> findAllByName(AptSearch aptSearch) {
        return aptRepository.findAllByString(aptSearch);
    }
}
