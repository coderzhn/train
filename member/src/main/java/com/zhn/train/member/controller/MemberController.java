package com.zhn.train.member.controller;

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
    public long register(String mobile){
        return memberService.register(mobile);
    }
}
