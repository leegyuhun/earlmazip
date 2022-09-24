package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.AptResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AptRepositoryTest {

    @Autowired
    AptRepository aptRepository;

    @Test
    void findAptInfo() {
//        String aptName = "메가트리아";
//        List<AptResponseDto> aptInfo = aptRepository.findAptInfo(aptName);
//        assertThat(aptInfo.get(0).getAptName()).isEqualTo(aptName);

    }
}