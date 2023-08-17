package com.zhn.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.zhn.train.common.util.SnowUtil;
import com.zhn.train.member.domain.Passenger;
import com.zhn.train.member.mapper.PassengerMapper;
import com.zhn.train.member.req.PassengerSaveReq;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    @Resource
    PassengerMapper passengerMapper;
    public void save(@Valid PassengerSaveReq req){
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }
}
