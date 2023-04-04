package com.earlmazip.service.impl;

import com.earlmazip.domain.IpBlock;
import com.earlmazip.repository.IpBlockRepository;
import com.earlmazip.service.IpBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpBlockServiceImpl implements IpBlockService {

//    IpBlockRepository ipBlockRepository;
//    @Autowired
//    public IpBlockServiceImpl(IpBlockRepository ipBlockRepository) {
//        this.ipBlockRepository = ipBlockRepository;
//    }

    private final IpBlockRepository ipBlockRepository;

    public IpBlockServiceImpl(IpBlockRepository ipBlockRepository) {
        this.ipBlockRepository = ipBlockRepository;
    }

    @Override
    public Boolean IsBlockIP(String ipAddress) {
        if (ipAddress.equals("")) return true;

        return ipBlockRepository.IsBlockIP(ipAddress);
    }

    @Override
    public Boolean AddBlockIP(String ipAddress) {
        if (ipAddress.equals("")) return true;

        return ipBlockRepository.AddBlockIP(ipAddress);
    }

    @Override
    public List<IpBlock> GetIPBlock() {
        return  ipBlockRepository.GetIPBlock();
    }
}
