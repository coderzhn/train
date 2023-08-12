package com.zhn.train.member.controller;

import com.zhn.train.common.resp.CommonResp;
import com.zhn.train.member.req.MemberRegisterReq;
import com.zhn.train.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/count")
    public Integer count(){
        return Math.toIntExact(memberService.count());
    }
    @PostMapping("/register")
    public CommonResp<Long> register(MemberRegisterReq req){
        long register = memberService.register(req);
//        CommonResp<Long> commonResp = new CommonResp<>();
//        commonResp.setContent(register);
//        return commonResp;
        return new CommonResp<>(register);
    }
}
