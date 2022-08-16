package com.imooc.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imooc.domain.UserFollowing;
import com.imooc.domain.UserMoment;
import com.imooc.domain.constant.UserMomentsConstant;
import com.imooc.service.UserFollowingService;
import com.imooc.websocket.WebSocketService;
import com.mysql.cj.util.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.name.server.address}")
    private String nameServerAddr;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    UserFollowingService userFollowingService;

    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }

    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS,"*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = list.get(0);
                if (msg==null){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String body = new String(msg.getBody());
                UserMoment moment = JSON.toJavaObject(JSONObject.parseObject(body), UserMoment.class);
                Long userId = moment.getUserId();
                List<UserFollowing> userFans = userFollowingService.getUserFans(userId);

                for (UserFollowing following:userFans){
                    String key="subscribed-"+following.getUserId();
                    String followingListStr = redisTemplate.opsForValue().get(key);
                    List<UserMoment> subScribedList;
                    if (StringUtils.isNullOrEmpty(followingListStr)){
                        subScribedList=new ArrayList<>();
                    }else{
                        subScribedList=JSONObject.parseArray(followingListStr,UserMoment.class);
                    }
                    subScribedList.add(moment);
                    redisTemplate.opsForValue().set(key,JSONObject.toJSONString(subScribedList));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }

    @Bean("danmuProducer")
    public DefaultMQProducer danmuProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_DANMES);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }

    @Bean("danmuConsumer")
    public DefaultMQPushConsumer danmuConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_DANMES);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentsConstant.TOPIC_DANMUS,"*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = list.get(0);
                byte[] body = msg.getBody();
                String bodyStr = new String(body);
                JSONObject jsonObject = JSONObject.parseObject(bodyStr);
                String sessionId = jsonObject.getString("sessionId");
                String message = jsonObject.getString("message");
                WebSocketService webSocketService = WebSocketService.WEBSOCKET_MAP.get(sessionId);
                if (webSocketService.getSession().isOpen()){
                    try{
                        webSocketService.sendMessage(message);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        return consumer;
    }
}
