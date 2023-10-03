package com.zhn.train.business.controller.admin;

import com.zhn.train.common.context.LoginMemberContext;
import com.zhn.train.common.resp.CommonResp;
import com.zhn.train.common.resp.PageResp;
import com.zhn.train.business.req.skTokenQueryReq;
import com.zhn.train.business.req.skTokenSaveReq;
import com.zhn.train.business.resp.skTokenQueryResp;
import com.zhn.train.business.service.skTokenService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/sk-token")
public class skTokenAdminController {

    @Resource
    private skTokenService skTokenService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody skTokenSaveReq req) {
        skTokenService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<skTokenQueryResp>> queryList(@Valid skTokenQueryReq req) {
        PageResp<skTokenQueryResp> list = skTokenService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        skTokenService.delete(id);
        return new CommonResp<>();
    }

}
