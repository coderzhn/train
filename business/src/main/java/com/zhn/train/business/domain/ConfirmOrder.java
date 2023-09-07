package com.zhn.train.business.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ConfirmOrder {
    private Long id;

    private Long memberId;

    private Date date;

    private String trainCode;

    private String start;

    private String end;

    private Long dailyTrainTicketId;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String tickets;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", date=").append(date);
        sb.append(", trainCode=").append(trainCode);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", dailyTrainTicketId=").append(dailyTrainTicketId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", tickets=").append(tickets);
        sb.append("]");
        return sb.toString();
    }
}