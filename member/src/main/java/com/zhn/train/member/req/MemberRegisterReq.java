package com.zhn.train.member.req;

import lombok.Data;

@Data
public class MemberRegisterReq {
    private String mobile;

    @Override
    public String toString() {
        return "MemberRegisterReq{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
