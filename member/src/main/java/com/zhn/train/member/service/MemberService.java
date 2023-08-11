package com.zhn.train.member.service;

import com.zhn.train.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
    public Long count(){
        return memberMapper.countByExample(null);
    }

}
