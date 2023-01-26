package com.earlmazip.service.impl;

import com.earlmazip.service.IpCountService;
import com.earlmazip.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class RequestServiceImpl implements RequestService {
    private Logger logger = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final String LOCALHOST_IPV4 = "127.0.0.1";
    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    private final String UNKNOWN = "unknown";
    @Override
    public String getClientIPAddress(HttpServletRequest request) throws UnknownHostException {
        String clientIPAddress = request.getHeader("X-Forwarded-For");
        if (!StringUtils.hasLength(clientIPAddress) || UNKNOWN.equals(clientIPAddress)) {
            clientIPAddress = request.getHeader("Proxy-Client-IP");
        }

        if (!StringUtils.hasLength(clientIPAddress) || UNKNOWN.equals(clientIPAddress)) {
            clientIPAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (!StringUtils.hasLength(clientIPAddress) || UNKNOWN.equals(clientIPAddress)) {
            clientIPAddress = request.getRemoteAddr();
            if (LOCALHOST_IPV4.equals(clientIPAddress) || LOCALHOST_IPV6.equals(clientIPAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    clientIPAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        if (StringUtils.hasLength(clientIPAddress) && clientIPAddress.length() > 15 && clientIPAddress.indexOf(",") > 0) {
            clientIPAddress = clientIPAddress.substring(0, clientIPAddress.indexOf(","));
        }
        return clientIPAddress;
    }
}
