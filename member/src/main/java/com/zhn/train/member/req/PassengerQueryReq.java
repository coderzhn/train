package com.zhn.train.member.req;

import lombok.Data;

@Data
public class PassengerQueryReq{

    private Long memberId;



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PassengerQueryReq{");
        sb.append("memberId=").append(memberId);
        sb.append('}');
        return sb.toString();
    }
}
