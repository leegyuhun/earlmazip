package com.earlmazip.utils;

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
}
