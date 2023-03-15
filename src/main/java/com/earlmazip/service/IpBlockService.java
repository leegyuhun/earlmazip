package com.earlmazip.service;

import com.earlmazip.domain.IpBlock;
import com.earlmazip.domain.IpCount;
import com.earlmazip.repository.IpBlockRepository;
import com.earlmazip.repository.IpCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IpBlockService {

    Boolean IsBlockIP(String ipAddress);

    Boolean AddBlockIP(String ipAddress);

    List<IpBlock> GetIPBlock();
}
