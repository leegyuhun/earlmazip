package com.earlmazip.controller;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.AptResponseDto;
import com.earlmazip.controller.dto.Message;
import com.earlmazip.controller.dto.TradeSearchCond;
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
            @RequestParam(value = "uaType", defaultValue = "UA01") String uaType) {
        Message msg = new Message();
        try {
            TradeSearchCond con = new TradeSearchCond();
            List<AptPriceResponseDto> tradeList = new ArrayList<>();
            String title = codeInfoService.getCodeName(sigunguCode);
            String url = "/api/v1/tradelistMonthly?sigunguCode=" + sigunguCode + "&year=" + year + "&mon=" + mon + "&landDong=" + landDong + "&uaTYpe=" + uaType;
            apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
            apiCallStatService.writeApiCallStat("API", "/api/v1/tradelistMonthly?sigunguCode=" + sigunguCode + "&year=" + year + "&mon=" + mon + "&landDong=" + landDong + "&uaTYpe=" + uaType, sigunguCode);

            if (sigunguCode.isEmpty() || year.isEmpty()) {
                msg.setStatus(Message.StatusEnum.BAD_REQUEST);
                if (sigunguCode.isEmpty()) {
                    msg.setMessage("Param [sigunguCode] is Empty.");
                } else {
                    msg.setMessage("Param [year] is Empty.");
                }
                msg.setCount(tradeList.size());
                msg.setData(tradeList);

                return new ResponseEntity<Message>(msg, HttpStatus.BAD_REQUEST);
            } else {
                con.setSigunguCode(sigunguCode);
                con.setDealYear(year);
                con.setDealMon(mon);
                con.setLandDong(landDong);
                con.setUseAreaType(uaType);

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
}