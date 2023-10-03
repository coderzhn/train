package com.zhn.train.business.domain;

import lombok.Data;

import java.util.Date;

@Data
public class skToken {
    private Long id;

    private Date date;

    private String trainCode;

    private Integer count;

    private Date createTime;

    private Date updateTime;



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", trainCode=").append(trainCode);
        sb.append(", count=").append(count);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}