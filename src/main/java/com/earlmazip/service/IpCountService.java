package com.earlmazip.service;

import com.earlmazip.domain.IpCount;
import com.earlmazip.repository.IpCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//public interface IpCountService {
@Service
@RequiredArgsConstructor
public class IpCountService {
    private final IpCountRepository ipCountRepository;

    @Transactional
    public void ipCounting(String ipAddress) {
        if (ipAddress.equals("")) return;
        ipCountRepository.IpCounting(ipAddress);
    }

    @Transactional
    public void updateIpInfo(IpCount pIpCount) {
        ipCountRepository.IpInfoUpdate(pIpCount);
    }

    public List<IpCount> GetIpHistory() {
        return ipCountRepository.GetIpHistory();
    }
//    void ipCounting(String ipAddress);
//
//    void updateIpInfo(IpCount pIpCount);
//
//    List<IpCount> GetIpHistory();
}
