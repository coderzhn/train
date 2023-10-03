package com.zhn.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhn.train.common.resp.PageResp;
import com.zhn.train.common.util.SnowUtil;
import com.zhn.train.business.domain.skToken;
import com.zhn.train.business.domain.skTokenExample;
import com.zhn.train.business.mapper.skTokenMapper;
import com.zhn.train.business.req.skTokenQueryReq;
import com.zhn.train.business.req.skTokenSaveReq;
import com.zhn.train.business.resp.skTokenQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class skTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(skTokenService.class);

    @Resource
    private skTokenMapper skTokenMapper;

    public void save(skTokenSaveReq req) {
        DateTime now = DateTime.now();
        skToken skToken = BeanUtil.copyProperties(req, skToken.class);
        if (ObjectUtil.isNull(skToken.getId())) {
            skToken.setId(SnowUtil.getSnowflakeNextId());
            skToken.setCreateTime(now);
            skToken.setUpdateTime(now);
            skTokenMapper.insert(skToken);
        } else {
            skToken.setUpdateTime(now);
            skTokenMapper.updateByPrimaryKey(skToken);
        }
    }

    public PageResp<skTokenQueryResp> queryList(skTokenQueryReq req) {
        skTokenExample skTokenExample = new skTokenExample();
        skTokenExample.setOrderByClause("id desc");
        skTokenExample.Criteria criteria = skTokenExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<skToken> skTokenList = skTokenMapper.selectByExample(skTokenExample);

        PageInfo<skToken> pageInfo = new PageInfo<>(skTokenList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<skTokenQueryResp> list = BeanUtil.copyToList(skTokenList, skTokenQueryResp.class);

        PageResp<skTokenQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        skTokenMapper.deleteByPrimaryKey(id);
    }
}
