package com.zhn.train.member.controller.admin;

import com.zhn.train.common.resp.CommonResp;
import com.zhn.train.common.resp.PageResp;
import com.zhn.train.member.req.TicketQueryReq;
import com.zhn.train.member.resp.TicketQueryResp;
import com.zhn.train.member.service.TicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

    @Resource
    private TicketService ticketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> queryList(@Valid TicketQueryReq req) {
        PageResp<TicketQueryResp> list = ticketService.queryList(req);
        return new CommonResp<>(list);
    }

}
