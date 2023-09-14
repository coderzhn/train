package com.zhn.train.business.req;

import com.zhn.train.common.req.PageReq;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DailyTrainTicketQueryReq that = (DailyTrainTicketQueryReq) o;
        return Objects.equals(trainCode, that.trainCode) && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trainCode, start, end, date);
    }
}
