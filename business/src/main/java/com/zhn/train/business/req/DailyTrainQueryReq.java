package com.zhn.train.business.req;

import com.zhn.train.common.req.PageReq;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DailyTrainQueryReq extends PageReq {
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Override
    public String toString() {
        return "DailyTrainQueryReq{" +
                "code='" + code + '\'' +
                ", date=" + date +
                "} " + super.toString();
    }
}
