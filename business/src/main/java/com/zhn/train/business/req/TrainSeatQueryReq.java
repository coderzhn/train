package com.zhn.train.business.req;

import com.zhn.train.common.req.PageReq;
import lombok.Data;

@Data
public class TrainSeatQueryReq extends PageReq {

    private String trainCode;

    @Override
    public String toString() {
        return "TrainSeatQueryReq{" +
                "trainCode='" + trainCode + '\'' +
                "} " + super.toString();
    }
}
