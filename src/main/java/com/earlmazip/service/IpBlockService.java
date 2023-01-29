package com.earlmazip.service;

import com.earlmazip.domain.IpBlock;
import com.earlmazip.domain.IpCount;
import com.earlmazip.repository.IpBlockRepository;
import com.earlmazip.repository.IpCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IpBlockService {

    private final IpBlockRepository ipBlockRepository;
    @Transactional
    public Boolean IsBlockIP(String ipAddress) {
        if (ipAddress.equals("")) return true;

        return ipBlockRepository.IsBlockIP(ipAddress);
    }

    @Transactional
    public Boolean AddBlockIP(String ipAddress) {
        if (ipAddress.equals("")) return true;

        return ipBlockRepository.AddBlockIP(ipAddress);
    }

    public List<IpBlock> GetIPBlock() {
        return  ipBlockRepository.GetIPBlock();
    }
}
