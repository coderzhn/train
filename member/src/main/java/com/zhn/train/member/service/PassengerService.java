package com.zhn.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.zhn.train.common.context.LoginMemberContext;
import com.zhn.train.common.util.SnowUtil;
import com.zhn.train.member.domain.Passenger;
import com.zhn.train.member.domain.PassengerExample;
import com.zhn.train.member.mapper.PassengerMapper;
import com.zhn.train.member.req.PassengerQueryReq;
import com.zhn.train.member.req.PassengerSaveReq;
import com.zhn.train.member.resp.PassengerQueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {
    @Resource
    PassengerMapper passengerMapper;
    public void save(@Valid PassengerSaveReq req){
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setMemberId(LoginMemberContext.getId());
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }
    public List<PassengerQueryResp> queryList(@Valid PassengerQueryReq req){
        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId())){
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);
        return BeanUtil.copyToList(passengerList, PassengerQueryResp.class);
    }
}
