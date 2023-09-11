package com.zhn.train.member.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
@Data
public class TicketQueryResp {

    /**
     * id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 会员id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long memberId;

    /**
     * 乘客id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long passengerId;

    /**
     * 乘客姓名
     */
    private String passengerName;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date trainDate;

    /**
     * 车次编号
     */
    private String trainCode;

    /**
     * 箱序
     */
    private Integer carriageIndex;

    /**
     * 排号|01, 02
     */
    private String seatRow;

    /**
     * 列号|枚举[SeatColEnum]
     */
    private String seatCol;

    /**
     * 出发站
     */
    private String startStation;

    /**
     * 出发时间
     */
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    /**
     * 到达站
     */
    private String endStation;

    /**
     * 到站时间
     */
    @JsonFormat(pattern = "HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    /**
     * 座位类型|枚举[SeatTypeEnum]
     */
    private String seatType;

    /**
     * 新增时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", passengerId=").append(passengerId);
        sb.append(", passengerName=").append(passengerName);
        sb.append(", trainDate=").append(trainDate);
        sb.append(", trainCode=").append(trainCode);
        sb.append(", carriageIndex=").append(carriageIndex);
        sb.append(", seatRow=").append(seatRow);
        sb.append(", seatCol=").append(seatCol);
        sb.append(", startStation=").append(startStation);
        sb.append(", startTime=").append(startTime);
        sb.append(", endStation=").append(endStation);
        sb.append(", endTime=").append(endTime);
        sb.append(", seatType=").append(seatType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}
