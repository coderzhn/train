package com.zhn.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.zhn.train.member.domain.Member;
import com.zhn.train.member.domain.MemberExample;
import com.zhn.train.member.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
    public Long count(){
        return memberMapper.countByExample(null);
    }
    public Long register(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if(CollUtil.isNotEmpty(list))
            throw new RuntimeException("手机号已注册");
        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

}
