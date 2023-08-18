package com.zhn.train.member.controller;

import com.zhn.train.common.context.LoginMemberContext;
import com.zhn.train.common.resp.CommonResp;
import com.zhn.train.member.req.PassengerQueryReq;
import com.zhn.train.member.req.PassengerSaveReq;
import com.zhn.train.member.resp.PassengerQueryResp;
import com.zhn.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/query-list")
    public CommonResp<List<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req){
        Long memberId = LoginMemberContext.getId();
        req.setMemberId(memberId);
        List<PassengerQueryResp> list = passengerService.queryList(req);
        return new CommonResp<>(list);
    }
}
