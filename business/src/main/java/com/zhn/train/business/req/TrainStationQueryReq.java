package com.zhn.train.business.req;

import com.zhn.train.common.req.PageReq;
import lombok.Data;

import java.util.Date;

@Data
public class TrainStationQueryReq extends PageReq {

    private Date date;
    private String trainCode;

    @Override
    public String toString() {
        return "TrainStationQueryReq{" +
                "date=" + date +
                ", trainCode='" + trainCode + '\'' +
                "} " + super.toString();
    }
}
