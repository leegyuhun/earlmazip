package com.earlmazip.controller;

import com.earlmazip.controller.dto.*;
import com.earlmazip.service.ApiCallStatService;
import com.earlmazip.service.ApiService;
import com.earlmazip.service.CodeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
    private final ApiCallStatService apiCallStatService;
    private final ApiService apiService;
    private final CodeInfoService codeInfoService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "sigunguCode", value = "구코드", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "year", value = "년도", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "mon", value = "월", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "landDong", value = "법정동", required = false, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "uaType", value = "평형대(NULL:전체,UA02:소형,UA03:중소형,UA04:중형,UA05:중대형,UA06:대형,UA07:국평,UA08:전용59)", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponse(code=200, message = "OK")
    @GetMapping("/v1/tradelistMonthly")
    public ResponseEntity<Message> getTradeListMonthlyV1(
            @RequestParam(value = "sigunguCode", defaultValue = "") String sigunguCode,
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "mon", defaultValue = "") String mon,
            @RequestParam(value = "landDong", defaultValue = "") String landDong,
            @RequestParam(value = "uaType", defaultValue = "") String uaType) {
        Message msg = new Message();
        System.out.println("?sigunguCode=" + sigunguCode + "&year=" + year + "&mon" + mon + "&landDong=" + landDong + "&uaType=" + uaType);
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
                mon = String.format("%02d", Integer.parseInt(mon));
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "sigunguCode", value = "구코드", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "year", value = "년도", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "mon", value = "월", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "leaseType", value = "임대유형(전세:0,월세:1)", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "landDong", value = "법정동", required = false, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "uaType", value = "평형대(NULL:전체,UA02:소형,UA03:중소형,UA04:중형,UA05:중대형,UA06:대형,UA07:국평,UA08:전용59)", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping("/v1/leaselistMonthly")
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
                mon = String.format("%02d", Integer.parseInt(mon));
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "sigunguCode", value = "구코드", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "year", value = "년도", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "uaType", value = "평형대(UA01:전체,UA02:소형,UA03:중소형,UA04:중형,UA05:중대형,UA06:대형,UA07:국평,UA08:전용59)", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping("/v1/statTradelistMonthly")
    public ResponseEntity<Message> getStatTradeListMonthlyV1(
            @RequestParam(value = "sigunguCode", defaultValue = "") String sigunguCode,
            @RequestParam(value = "year", defaultValue = "") String year,
            @RequestParam(value = "uaType", defaultValue = "UA01") String uaType) {
        Message msg = new Message();
        System.out.println("?sigunguCode=" + sigunguCode + "&year=" + year + "&uaType=" + uaType);
        try {
            List<StatResponseDto> tradeList = new ArrayList<>();

            if (sigunguCode.isEmpty() || year.isEmpty()) {
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
                String url = "/api/v1/statTradelistMonthly?sigunguCode=" + sigunguCode + "&year=" + year + "&uaTYpe=" + uaType;
                apiCallStatService.writeApiCallStatDetail(url, sigunguCode, title);
                apiCallStatService.writeApiCallStat("API", "/api/v1/statTradelistMonthly?sigunguCode=" + sigunguCode + "&year=" + year + "&uaTYpe=" + uaType, sigunguCode);

                con.setSigunguCode(sigunguCode);
                con.setDealYear(year);
                con.setUaType(uaType);

                tradeList = apiService.getStatTradeListMonthlyV1(sigunguCode, year, uaType);

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