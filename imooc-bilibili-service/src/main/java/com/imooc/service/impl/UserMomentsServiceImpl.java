package com.imooc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.imooc.dao.UserMomentsDao;
import com.imooc.domain.UserMoment;
import com.imooc.domain.constant.UserMomentsConstant;
import com.imooc.service.UserMomentsService;
import com.imooc.utils.RocketMQUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class UserMomentsServiceImpl implements UserMomentsService {

    @Autowired
    UserMomentsDao userMomentsDao;

    @Autowired
    DefaultMQProducer producer;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public void addUserMoments(UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        Date date = new Date();
        userMoment.setCreateTime(date);
        userMoment.setUpdateTime(date);
        userMomentsDao.addUserMoments(userMoment);
        Message msg = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoment).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.syncSendMsg(producer,msg);
    }

    @Override
    public List<UserMoment> getUserSubscribedMoments(Long userId) {
        String key="subscribed-"+userId;
        String momentsStr = redisTemplate.opsForValue().get(key);
        return JSONObject.parseArray(momentsStr, UserMoment.class);
    }

}
