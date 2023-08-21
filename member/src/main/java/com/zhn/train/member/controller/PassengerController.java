package com.zhn.train.member.controller;

import com.zhn.train.common.context.LoginMemberContext;
import com.zhn.train.common.resp.CommonResp;
import com.zhn.train.common.resp.PageResp;
import com.zhn.train.member.domain.Passenger;
import com.zhn.train.member.req.PassengerQueryReq;
import com.zhn.train.member.req.PassengerSaveReq;
import com.zhn.train.member.resp.PassengerQueryResp;
import com.zhn.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req){
        Long memberId = LoginMemberContext.getId();
        req.setMemberId(memberId);
        PageResp<PassengerQueryResp> list = passengerService.queryList(req);
        return new CommonResp<>(list);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp<Passenger> delete(@PathVariable Long id){
        passengerService.delete(id);
        return new CommonResp<>();
    }
}
