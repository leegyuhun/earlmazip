package com.earlmazip.service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

public interface RequestService {
    String getClientIPAddress(HttpServletRequest request) throws UnknownHostException;
}
