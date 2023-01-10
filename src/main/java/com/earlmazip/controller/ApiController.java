package com.earlmazip.controller;

import com.earlmazip.controller.dto.*;
import com.earlmazip.repository.AptSearch;
import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.ApiService;
import com.earlmazip.service.AptService;
import com.earlmazip.service.CodeInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ApiController {
    private final ApiCallStatService apiCallStatService;
    private final ApiService apiService;
    private final CodeInfoService codeInfoService;

    @GetMapping("/api/v1/tradelistMonthly")
    public ResponseEntity<Message> getTradeListMonthlyV1(
            @RequestParam(value = "sigunguCode", defaultValue = "") String sigunguCode,
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "mon", defaultValue = "") String mon,
            @RequestParam(value = "landDong", defaultValue = "") String landDong,
            @RequestParam(value = "uaType", defaultValue = "") String uaType) {
        Message msg = new Message();
        try {
            List<AptPriceResponseDto> tradeList = new ArrayList<>();

            if (sigunguCode.isEmpty() || year.isEmpty() || mon.isEmpty()) {
                msg.setStatus(Message.StatusEnum.BAD_REQUEST);
                if (sigunguCode.isEmpty()) {
                    msg.setMessage("Param [sigunguCode] is Empty.");
                } else if (year.isEmpty()) {
                    msg.setMessage("Param [year] is Empty.");
                } else {
                    msg.setMessage("Param [mon] is Empty.");
                }
                msg.setCount(tradeList.size());
                msg.setData(tradeList);

                return new ResponseEntity<Message>(msg, HttpStatus.BAD_REQUEST);
            } else {
                TradeSearchCond con = new TradeSearchCond();
                String title = codeInfoService.getCodeName(sigunguCode);
                String url = "/api/v1/tradelistMonthly?sigunguCode=" + sigunguCode + "&year=" + year + "&mon=" + mon + "&landDong=" + landDong + "&uaTYpe=" + uaType;
                apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
                apiCallStatService.writeApiCallStat("API", "/api/v1/tradelistMonthly?sigunguCode=" + sigunguCode + "&year=" + year + "&mon=" + mon + "&landDong=" + landDong + "&uaTYpe=" + uaType, sigunguCode);

                con.setSigunguCode(sigunguCode);
                con.setDealYear(year);
                con.setDealMon(mon);
                con.setLandDong(landDong);
                con.setUaType(uaType);

                tradeList = apiService.getTradeListMonthlyV1(con);

                msg.setStatus(Message.StatusEnum.OK);
                msg.setMessage("success");
                msg.setCount(tradeList.size());
                msg.setData(tradeList);

                return new ResponseEntity<Message>(msg, HttpStatus.OK);
            }
        } catch (Exception exception) {
            msg.setStatus(Message.StatusEnum.BAD_REQUEST);
            msg.setMessage(exception.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/api/v1/leaselistMonthly")
    public ResponseEntity<Message> getLeaseListMonthlyV1(
            @RequestParam(value = "sigunguCode", defaultValue = "") String sigunguCode,
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "mon", defaultValue = "") String mon,
            @RequestParam(value = "leaseType", defaultValue = "") String leaseType,
            @RequestParam(value = "landDong", defaultValue = "") String landDong,
            @RequestParam(value = "uaType", defaultValue = "") String uaType) {
        Message msg = new Message();
        try {
            List<AptLeaseResponseDto> leaseList = new ArrayList<>();
            if (sigunguCode.isEmpty() || year.isEmpty() || mon.isEmpty()) {
                msg.setStatus(Message.StatusEnum.BAD_REQUEST);
                if (sigunguCode.isEmpty()) {
                    msg.setMessage("Param [sigunguCode] is Empty.");
                } else if (year.isEmpty()) {
                    msg.setMessage("Param [year] is Empty.");
                } else {
                    msg.setMessage("Param [mon] is Empty.");
                }
                msg.setCount(leaseList.size());
                msg.setData(leaseList);

                return new ResponseEntity<Message>(msg, HttpStatus.BAD_REQUEST);
            } else {
                TradeSearchCond con = new TradeSearchCond();
                String title = codeInfoService.getCodeName(sigunguCode);
                String url = "/api/v1/leaselistMonthly?sigunguCode=" + sigunguCode + "&year=" + year + "&mon=" + mon + "&landDong=" + landDong + "&uaTYpe=" + uaType;
                apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
                apiCallStatService.writeApiCallStat("API", "/api/v1/leaselistMonthly?sigunguCode=" + sigunguCode + "&year=" + year + "&mon=" + mon + "&landDong=" + landDong + "&uaTYpe=" + uaType, sigunguCode);

                con.setSigunguCode(sigunguCode);
                con.setDealYear(year);
                con.setDealMon(mon);
                con.setLeaseType(leaseType);
                con.setLandDong(landDong);
                con.setUaType(uaType);

                leaseList = apiService.getLeaseListMonthlyV1(con);

                msg.setStatus(Message.StatusEnum.OK);
                msg.setMessage("success");
                msg.setCount(leaseList.size());
                msg.setData(leaseList);

                return new ResponseEntity<Message>(msg, HttpStatus.OK);
            }
        } catch (Exception exception) {
            msg.setStatus(Message.StatusEnum.BAD_REQUEST);
            msg.setMessage(exception.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);
        }
    }
}