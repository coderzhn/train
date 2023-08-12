package com.zhn.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.zhn.train.common.exception.BusinessException;
import com.zhn.train.common.exception.BusinessExceptionEnum;
import com.zhn.train.member.domain.Member;
import com.zhn.train.member.domain.MemberExample;
import com.zhn.train.member.mapper.MemberMapper;
import com.zhn.train.member.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;

    public Long register(MemberRegisterReq req) {
        String mobile = req.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if(CollUtil.isNotEmpty(list))
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

}
