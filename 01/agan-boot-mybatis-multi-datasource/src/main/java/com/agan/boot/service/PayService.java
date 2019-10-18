package com.agan.boot.service;

import com.agan.boot.mapper.account.entity.CapitalAccount;
import com.agan.boot.mapper.account.mapper.CapitalAccountMapper;
import com.agan.boot.mapper.redaccount.entity.RedPacketAccount;
import com.agan.boot.mapper.redaccount.mapper.RedPacketAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@Service
public class PayService {

    @Autowired
    private CapitalAccountMapper capitalAccountMapper;
    @Autowired
    private RedPacketAccountMapper redPacketAccountMapper;

    /**
     * 账户余额 减钱
     * @param userId
     * @param account
     */
    @Transactional(rollbackFor = Exception.class)
    public void payAccount(int userId,int account) {
        CapitalAccount ca=new CapitalAccount();
        ca.setUserId(userId);
        CapitalAccount capitalDTO=this.capitalAccountMapper.selectOne(ca);
//        System.out.println(capitalDTO);
        //从账户里面扣除钱
        capitalDTO.setBalanceAmount(capitalDTO.getBalanceAmount()-account);
        this.capitalAccountMapper.updateByPrimaryKey(capitalDTO);

    }

    /**
     * 红包余额 加钱
     * @param userId
     * @param account
     */
    @Transactional(rollbackFor = Exception.class)
    public void payRedPacket(int userId,int account) {
        RedPacketAccount red= new RedPacketAccount();
        red.setUserId(userId);
        RedPacketAccount redDTO=this.redPacketAccountMapper.selectOne(red);
//        System.out.println(redDTO);
        //红包余额 加钱
        redDTO.setBalanceAmount(redDTO.getBalanceAmount()+account);
        this.redPacketAccountMapper.updateByPrimaryKey(redDTO);

        //int i=9/0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void pay(int fromUserId,int toUserId,int account){
        //账户余额 减钱
        this.payAccount(fromUserId,account);
        //红包余额 加钱
        this.payRedPacket(toUserId,account);
    }
}
