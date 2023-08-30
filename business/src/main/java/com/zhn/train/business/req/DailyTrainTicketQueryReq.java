package com.zhn.train.business.req;

import com.zhn.train.common.req.PageReq;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class DailyTrainTicketQueryReq extends PageReq {
    private String trainCode;
    private String start;
    private String end;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Override
    public String toString() {
        return "DailyTrainTicketQueryReq{" +
                "trainCode='" + trainCode + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", date=" + date +
                "} " + super.toString();
    }
}
