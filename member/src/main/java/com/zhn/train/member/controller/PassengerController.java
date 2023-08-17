package com.zhn.train.member.controller;

import com.zhn.train.common.resp.CommonResp;
import com.zhn.train.member.req.PassengerSaveReq;
import com.zhn.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Resource
    PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody PassengerSaveReq req){
        passengerService.save(req);
        return new CommonResp();
    }
}
