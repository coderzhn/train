package com.zhn.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhn.train.common.context.LoginMemberContext;
import com.zhn.train.common.resp.PageResp;
import com.zhn.train.common.util.SnowUtil;
import com.zhn.train.member.domain.${Domain};
import com.zhn.train.member.domain.${Domain}Example;
import com.zhn.train.member.mapper.${Domain}Mapper;
import com.zhn.train.member.req.${Domain}QueryReq;
import com.zhn.train.member.req.${Domain}SaveReq;
import com.zhn.train.member.resp.${Domain}QueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${Domain}Service {
    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Service.class);
    @Resource
    ${Domain}Mapper ${domain}Mapper;
    public void save(@Valid ${Domain}SaveReq req){
        DateTime now = DateTime.now();
        ${Domain} ${domain} = BeanUtil.copyProperties(req, ${Domain}.class);
        if (ObjectUtil.isNull(${domain}.getId())){
            ${domain}.setMemberId(LoginMemberContext.getId());
            ${domain}.setId(SnowUtil.getSnowflakeNextId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.insert(${domain});
        }else {
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.updateByPrimaryKey(${domain});
        }
    }
    public PageResp<${Domain}QueryResp> queryList(@Valid ${Domain}QueryReq req){
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId())){
            criteria.andMemberIdEqualTo(req.getMemberId());
        }


        LOG.info("查询页码:{}",req.getPage());
        LOG.info("每页条数:{}",req.getSize());
        PageHelper.startPage(req.getPage(),req.getSize());
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);

        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        LOG.info("总行数:{}",pageInfo.getTotal());
        LOG.info("总页数:{}",pageInfo.getPages());
        List<${Domain}QueryResp> list = BeanUtil.copyToList(${domain}List, ${Domain}QueryResp.class);
        PageResp<${Domain}QueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
