package com.zhn.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhn.train.business.domain.*;
import com.zhn.train.business.enums.ConfirmOrderStatusEnum;
import com.zhn.train.business.enums.SeatColEnum;
import com.zhn.train.business.enums.SeatTypeEnum;
import com.zhn.train.business.mapper.ConfirmOrderMapper;
import com.zhn.train.business.req.ConfirmOrderDoReq;
import com.zhn.train.business.req.ConfirmOrderQueryReq;
import com.zhn.train.business.req.ConfirmOrderTicketReq;
import com.zhn.train.business.resp.ConfirmOrderQueryResp;
import com.zhn.train.common.context.LoginMemberContext;
import com.zhn.train.common.exception.BusinessException;
import com.zhn.train.common.exception.BusinessExceptionEnum;
import com.zhn.train.common.resp.PageResp;
import com.zhn.train.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConfirmOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderService.class);

    @Resource
    private ConfirmOrderMapper confirmOrderMapper;
    @Resource
    private DailyTrainTicketService dailyTrainTicketService;
    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    public void save(ConfirmOrderDoReq req) {
        DateTime now = DateTime.now();
        ConfirmOrder confirmOrder = BeanUtil.copyProperties(req, ConfirmOrder.class);
        if (ObjectUtil.isNull(confirmOrder.getId())) {
            confirmOrder.setId(SnowUtil.getSnowflakeNextId());
            confirmOrder.setCreateTime(now);
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.insert(confirmOrder);
        } else {
            confirmOrder.setUpdateTime(now);
            confirmOrderMapper.updateByPrimaryKey(confirmOrder);
        }
    }

    public PageResp<ConfirmOrderQueryResp> queryList(ConfirmOrderQueryReq req) {
        ConfirmOrderExample confirmOrderExample = new ConfirmOrderExample();
        confirmOrderExample.setOrderByClause("id desc");
        ConfirmOrderExample.Criteria criteria = confirmOrderExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<ConfirmOrder> confirmOrderList = confirmOrderMapper.selectByExample(confirmOrderExample);

        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrderList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<ConfirmOrderQueryResp> list = BeanUtil.copyToList(confirmOrderList, ConfirmOrderQueryResp.class);

        PageResp<ConfirmOrderQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        confirmOrderMapper.deleteByPrimaryKey(id);
    }

    public void doConfirm(ConfirmOrderDoReq req) {
        // 省略业务数据校验
        // 保存确认订单表，状态初始
        ConfirmOrder confirmOrder = new ConfirmOrder();
        DateTime now = DateTime.now();
        confirmOrder.setId(SnowUtil.getSnowflakeNextId());
        confirmOrder.setMemberId(LoginMemberContext.getId());
        Date date = req.getDate();
        String trainCode = req.getTrainCode();
        String end = req.getEnd();
        String start = req.getStart();
        List<ConfirmOrderTicketReq> tickets = req.getTickets();
        confirmOrder.setCreateTime(now);
        confirmOrder.setUpdateTime(now);
        confirmOrder.setDate(date);
        confirmOrder.setTrainCode(trainCode);
        confirmOrder.setStart(start);
        confirmOrder.setEnd(end);
        confirmOrder.setDailyTrainTicketId(req.getDailyTrainTicketId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        confirmOrder.setTickets(JSON.toJSONString(req.getTickets()));
        confirmOrderMapper.insert(confirmOrder);
        //查出余票记录，需要得到真实的库存
        DailyTrainTicket dailyTrainTicket = dailyTrainTicketService.selectByUnique(date, trainCode, start, end);
        LOG.info("查出余票记录:{}",dailyTrainTicket);
        // 扣减余票数量，并判断余票是否重组
        reduceTickets(req, dailyTrainTicket);
        // 计算相对第一个座位的偏移量
        // 比如选择的是C1，D2，则偏移值是:[0，5]
        // 比如选择的是A1,B1,C1，则偏移值是:[0，1，2]
        ConfirmOrderTicketReq ticketReq0 = tickets.get(0);
        if(StrUtil.isNotBlank(ticketReq0.getSeat())){
            LOG.info("本次购票有选座");
            // 查出本次座位的座位类型都有哪些列，用于计算所选座位与第一个座位的偏离值
            List<SeatColEnum> colEnumList = SeatColEnum.getColsByType(ticketReq0.getSeatTypeCode());
            LOG.info("本次选座的座位类型包含的列:{}",colEnumList);
            //组成和前端两派选座一样的列表，用于做参照的座位列表，例如: referSeatList = {A1,C1,D1,F1,A2,C2,D2,F2}
            List<String> referSeatList = new ArrayList<>();
            for (int i = 0; i <= 2; i++) {
                for (SeatColEnum seatColnum:colEnumList) {
                    referSeatList.add(seatColnum.getCode()+i);
                }
            }
            LOG.info("用于做参照的的两排座位:{}",referSeatList);
            List<Integer> offsetList = new ArrayList<>();
            List<Integer> absoluteOffSetList = new ArrayList<>();
            //绝对偏移量，即，在参考座位列表中的位置
            for (ConfirmOrderTicketReq ticketReq: tickets){
                int index = referSeatList.indexOf(ticketReq.getSeat());
                absoluteOffSetList.add(index);
            }
            LOG.info("计算得到所有座位的绝对偏移值:{}",absoluteOffSetList);
            for(Integer index: absoluteOffSetList){
                int offset = index - absoluteOffSetList.get(0);
                offsetList.add(offset);
            }
            getSeat(date,trainCode,ticketReq0.getSeatTypeCode(),
                    ticketReq0.getSeat().split("")[0],//从A1得到A
                    offsetList,
                    dailyTrainTicket.getStartIndex(),
                    dailyTrainTicket.getEndIndex());
            LOG.info("计算得到所有座位的相对第一个座位的偏移值:{}",offsetList);
        }else {
            LOG.info("本次购票没有选座");
            for (ConfirmOrderTicketReq ticketReq: tickets){
                getSeat(date,
                        trainCode,
                        ticketReq0.getSeatTypeCode(),
                        null,
                        null,
                        dailyTrainTicket.getStartIndex(),
                        dailyTrainTicket.getEndIndex()
                );
            }

        }

        //TODO 选座
        //TODO 挑选符合条件的座位，如果这个车厢不满足，则进入下一个车厢
        //TODO 选中座位后事务处理
        //TODO 座位表修改售卖情况sell
        //TODO 余票详情表修改余票
        //TODO 为会员增加购票记录
        //TODO 更新确认订单为成功

    }

    /**
     * 选座位，如果有选座 则一次性挑完，如果无选座，则一个一个挑选
     * @param date
     * @param trainCode
     * @param seatType
     * @param column
     * @param offset
     */
    private void getSeat(Date date, String trainCode, String seatType,String column,List<Integer> offset,
                         Integer startIndex,Integer endIndex){
        List<DailyTrainCarriage> carriageList = dailyTrainCarriageService.selectBySeatType(date, trainCode, seatType);
        LOG.info("共查出{}个符合条件的车厢",carriageList.size());
        //一个一个车厢获取数据
        for(DailyTrainCarriage dailyTrainCarriage:carriageList){
            LOG.info(" 开始从车厢{}选座",dailyTrainCarriage.getIndex());
            List<DailyTrainSeat> seatList = dailyTrainSeatService.selectByCarriage(date, trainCode, dailyTrainCarriage.getIndex());
            LOG.info("车厢{}的座位数:{}",dailyTrainCarriage.getIndex(),seatList.size());
            for(DailyTrainSeat dailyTrainSeat:seatList){
                boolean isChoose = calSell(dailyTrainSeat, startIndex, endIndex);
                if(isChoose){
                    LOG.info("选中座位:");
                    return;
                }else {
                    continue;
                }

            }
        }

    }

    /**
     * 计算某座位在区间是否可卖
     * 例如:sell = 100001，本次购买区间站1~4，则区间已售000
     * 全部是0，表示这个区间可买，只要有1，就表示区间内已售过票
     *
     * 选中后，要计算购票后的sell，比如原来是1000，本次购买区间站1~4
     * 方案:构造本次购票造成的售卖信息01110，和原sell 10001按位或，最终得到11111
     * @param dailyTrainSeat
     */
    private boolean calSell(DailyTrainSeat dailyTrainSeat,Integer startIndex,Integer endIndex){
        String sell = dailyTrainSeat.getSell();
        String sellPart = sell.substring(startIndex, endIndex);
        if(Integer.parseInt(sellPart) > 0){
            LOG.info("座位{}在本次车站区间{}~{}已售过票，不可选中该座位",dailyTrainSeat.getCarriageSeatIndex(),startIndex,endIndex);
            return false;
        }else {
            LOG.info("座位{}在本次车站区间{}~{}未售过票，可选中该座位",dailyTrainSeat.getCarriageSeatIndex(),startIndex,endIndex);
            // 111
            String curSell = sellPart.replace('0', '1');
            // 0111
            StrUtil.fillBefore(curSell,'0',endIndex);
            // 01110
            StrUtil.fillAfter(curSell,'0',sell.length());
            // 当前区间售票信息curSell 01110 与库里的已售信息sell 00001按位或，即可得到该座位卖出此票后的售票详情
            //15（01111）
            int newSellInt = NumberUtil.binaryToInt(curSell) | NumberUtil.binaryToInt(sell);
            String newSell = NumberUtil.getBinaryStr(newSellInt);
            newSell = StrUtil.fillBefore(newSell, '0', sell.length());
            LOG.info("座位{}被选中，原售票信息:{}，车站区间: {}~{}，即: {}，最终售票信息:{}"
            ,dailyTrainSeat.getCarriageSeatIndex(),sell,startIndex,endIndex,curSell,newSell);
            dailyTrainSeat.setSell(newSell);
            return true;
        }

    }
    private void reduceTickets(ConfirmOrderDoReq req, DailyTrainTicket dailyTrainTicket) {
        for (ConfirmOrderTicketReq ticketReq: req.getTickets()) {
            String seatTypeCode = ticketReq.getSeatTypeCode();
            SeatTypeEnum seatTypeEnum = EnumUtil.getBy(SeatTypeEnum::getCode, seatTypeCode);
            switch(seatTypeEnum){
                case YDZ ->  {
                    int countLeft = dailyTrainTicket.getYdz() -1;
                    if(countLeft < 0){
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYdz(countLeft);
                }
                case EDZ ->  {
                    int countLeft = dailyTrainTicket.getEdz() -1;
                    if(countLeft < 0){
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setEdz(countLeft);
                }
                case RW ->  {
                    int countLeft = dailyTrainTicket.getRw() -1;
                    if(countLeft < 0){
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setRw(countLeft);
                }
                case YW ->  {
                    int countLeft = dailyTrainTicket.getYw() -1;
                    if(countLeft < 0){
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYw(countLeft);
                }
            }
        }
    }

}
