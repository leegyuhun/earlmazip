package com.earlmazip.controller;

import com.earlmazip.domain.IpCount;
import com.earlmazip.service.IpBlockService;
import com.earlmazip.service.IpCountService;
import com.earlmazip.service.SiteInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@Slf4j
@RequiredArgsConstructor
public class IpInfoController {
    private final IpCountService ipCountService;
    private final SiteInfoService siteInfoService;
    private final IpBlockService ipBlockService;

    public Boolean isIPCountryKOR(String ipAddress) throws IOException {
        String service_key = siteInfoService.findSiteInfo("SERVICE_KEY");

        URL url = new URL("http://apis.data.go.kr/B551505/whois/ip_address?serviceKey=" + service_key + "&answer=JSON&query=" + ipAddress);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("conn.getResponseCode() = " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        System.out.println(sb.toString());

        JSONObject jObject = new JSONObject(sb.toString());
        JSONObject response = (JSONObject)jObject.get("response");
        JSONObject result = (JSONObject)response.get("result");
        if (result.getString("result_code").equals("10000")) {
            IpCount ipCount = new IpCount();
            JSONObject whois = (JSONObject) response.get("whois");
            ipCount.setIpAddress(whois.getString("query"));
            if (whois.has("countryCode")) {
                ipCount.setCountryCode(whois.getString("countryCode"));
            }
            if (ipCount.getCountryCode().equals("KR") || ipCount.getCountryCode().equals("none")) {
                return true;
            } else {
                return false;
            }
//            if (!ipCount.getCountryCode().equals("KR") || !ipCount.getCountryCode().equals("none")) {
//                ipBlockService.AddBlockIP(ipAddress);
//            }
        } else {
            return false;
        }
    }

    public void BlockIP_NotKor(String ipAddress) throws IOException {
        String service_key = siteInfoService.findSiteInfo("SERVICE_KEY");

        URL url = new URL("http://apis.data.go.kr/B551505/whois/ip_address?serviceKey=" + service_key + "&answer=JSON&query=" + ipAddress);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("conn.getResponseCode() = " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        System.out.println(sb.toString());

        JSONObject jObject = new JSONObject(sb.toString());
        JSONObject response = (JSONObject)jObject.get("response");
        JSONObject result = (JSONObject)response.get("result");
        if (result.getString("result_code").equals("10000")) {
            IpCount ipCount = new IpCount();
            JSONObject whois = (JSONObject)response.get("whois");
            ipCount.setIpAddress(whois.getString("query"));
            if (whois.has("countryCode")) {
                ipCount.setCountryCode(whois.getString("countryCode"));
            }
            if (!ipCount.getCountryCode().equals("KR")) {
                ipBlockService.AddBlockIP(ipAddress);
            }
//            if (!ipCount.getCountryCode().equals("KR") || !ipCount.getCountryCode().equals("none")) {
//                ipBlockService.AddBlockIP(ipAddress);
//            }
        }
    }

    public void MergeIpInformation(String ipAddress) throws IOException {
        String service_key = siteInfoService.findSiteInfo("SERVICE_KEY");

        URL url = new URL("http://apis.data.go.kr/B551505/whois/ip_address?serviceKey=" + service_key + "&answer=JSON&query=" + ipAddress);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("conn.getResponseCode() = " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
        // 11. 전달받은 데이터 확인.
        System.out.println(sb.toString());

        JSONObject jObject = new JSONObject(sb.toString());
        JSONObject response = (JSONObject)jObject.get("response");
        JSONObject result = (JSONObject)response.get("result");
        if (result.getString("result_code").equals("10000")) {
            IpCount ipCount = new IpCount();
            JSONObject whois = (JSONObject)response.get("whois");
            ipCount.setIpAddress(whois.getString("query"));
            if (whois.has("countryCode")) {
                ipCount.setCountryCode(whois.getString("countryCode"));
            }
            if (ipCount.getCountryCode().equals("KR")) {
                JSONObject korean = (JSONObject)whois.get("korean");
                if (korean.has("ISP")) {
                    JSONObject ISP = (JSONObject) korean.get("ISP");
                    JSONObject netinfo = (JSONObject) ISP.get("netinfo");
                    ipCount.setIspName(netinfo.getString("orgName"));
                    ipCount.setIspAddr(netinfo.getString("addr"));
                }
                if (korean.has("user")) {
                    JSONObject user = (JSONObject) korean.get("user");
                    JSONObject netinfo2 = (JSONObject) user.get("netinfo");
                    ipCount.setUserName(netinfo2.getString("orgName"));
                    ipCount.setUserAddr(netinfo2.getString("addr"));
                }
            }
            ipCountService.updateIpInfo(ipCount);
        }
    }
}
