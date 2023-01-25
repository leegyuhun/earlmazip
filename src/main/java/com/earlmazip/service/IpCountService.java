package com.earlmazip.service;

import com.earlmazip.domain.IpCount;
import com.earlmazip.repository.IpCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IpCountService {

    private final IpCountRepository ipCountRepository;

    @Transactional
    public void ipCounting(String ipAddress) {
        if (ipAddress.equals("")) return;
        ipCountRepository.IpCounting(ipAddress);
    }

    public List<IpCount> GetIpHistory() {
        return ipCountRepository.GetIpHistory();
    }
}
