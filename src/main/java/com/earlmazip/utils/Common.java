package com.earlmazip.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    /**
     * term으로 들어온 숫자만큼 현재년도에서 빼줌
     * @param term
     * @return
     */
    public static String calcYearByTerm(int term) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int nowInt = Integer.parseInt(date.substring(0,4));
        // 현재날짜-term = 조회 기준일자
        return Integer.toString(nowInt - term);
    }

    /**
     * term으로 들어온 숫자만큼 현재년도에서 빼줌
     * @param term
     * @return
     */
    public static String calcYearByTerm(String term) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int termInt = Integer.parseInt(term);
        int nowInt = Integer.parseInt(date.substring(0,4));
        // 현재날짜-term = 조회 기준일자
        return Integer.toString(nowInt - termInt);
    }

    /**
     * (NowYear - term) ~ Now String으로 변환
     * @param term
     * @return
     */
    public static String makeTermString(int term) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int nowInt = Integer.parseInt(date.substring(0,4));
        int prevInt = nowInt - term;
        if (nowInt == prevInt) {
            return Integer.toString(nowInt);
        } else {
            return prevInt + " ~ " + nowInt;
        }
    }

    /**
     * (NowYear - term) ~ Now String으로 변환
     * @param term
     * @return
     */
    public static String makeTermString(String term) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int nowInt = Integer.parseInt(date.substring(0,4));
        int termInt = Integer.parseInt(term);
        int prevInt = nowInt - termInt;
        if (nowInt == prevInt) {
            return Integer.toString(nowInt);
        } else {
            return prevInt + " ~ " + nowInt;
        }
    }

    public static String getClientIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip==null) ip = request.getRemoteAddr();
        return ip;
//        String ip = StringUtils.trimToNull(TextUtils.getSplitValue(request.getHeader("X-Forwarded-For"), ",", 0));

//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("x-real-ip");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("x-original-forwarded-for");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("HTTP_X_FORWARDED");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("HTTP_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("HTTP_FORWARDED");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("HTTP_VIA");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getHeader("REMOTE_ADDR");
//        }
//        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
//            ip = request.getRemoteAddr();
//        }

//        return ip;
    }
}
