package com.imooc.service;

import com.imooc.domain.UserMoment;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public interface UserMomentsService {
    void addUserMoments(UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException;

    List<UserMoment> getUserSubscribedMoments(Long userId);
}
