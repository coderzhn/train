package com.zhn.train.business.controller;

import com.zhn.train.business.req.DailyTrainTicketQueryReq;
import com.zhn.train.business.resp.DailyTrainTicketQueryResp;
import com.zhn.train.business.service.DailyTrainTicketService;
import com.zhn.train.common.resp.CommonResp;
import com.zhn.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily-train-ticket")
public class DailyTrainTicketController {

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq req) {
        PageResp<DailyTrainTicketQueryResp> list = dailyTrainTicketService.queryList(req);
        return new CommonResp<>(list);
    }

}
