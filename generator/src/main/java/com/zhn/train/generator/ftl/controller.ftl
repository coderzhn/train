package com.zhn.train.member.controller;

import com.zhn.train.common.context.LoginMemberContext;
import com.zhn.train.common.resp.CommonResp;
import com.zhn.train.common.resp.PageResp;
import com.zhn.train.member.domain.${Domain};
import com.zhn.train.member.req.${Domain}QueryReq;
import com.zhn.train.member.req.${Domain}SaveReq;
import com.zhn.train.member.resp.${Domain}QueryResp;
import com.zhn.train.member.service.${Domain}Service;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${do_main}")
public class ${Domain}Controller {
    @Resource
    ${Domain}Service ${domain}Service;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody ${Domain}SaveReq req){
        ${domain}Service.save(req);
        return new CommonResp();
    }
    @GetMapping("/query-list")
    public CommonResp<PageResp<${Domain}QueryResp>> queryList(@Valid ${Domain}QueryReq req){
        Long memberId = LoginMemberContext.getId();
        req.setMemberId(memberId);
        PageResp<${Domain}QueryResp> list = ${domain}Service.queryList(req);
        return new CommonResp<>(list);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp<${Domain}> delete(@PathVariable Long id){
        ${domain}Service.delete(id);
        return new CommonResp<>();
    }
}
