package com.wdx.bootplum.common.config;

import com.wdx.bootplum.common.constant.Constant;
import com.wdx.bootplum.common.redis.Receiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * <p>
 * RedisListenerConfig
 * </p>
 *
 * @author gqc
 * @since 2019-04-18
 */
@Configuration
public class RedisListenerConfig {

    Receiver receiver;
 /*   *//**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param connectionFactory
     * @return
     *//*
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        //....根据自己模块添加消息监听器
        container.addMessageListener(listenerAdapterList(receiver), new PatternTopic(Constant.RECEIVELIST));
        container.addMessageListener(listenerAdapterSingle(receiver), new PatternTopic(Constant.RECEIVESINGLE));
        return container;
    }

    *//**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法:单条excel的监听
     * @param receiver
     * @return
     *//*
    @Bean
    MessageListenerAdapter listenerAdapterSingle(Receiver receiver) {
        return new MessageListenerAdapter(receiver, Constant.SINGLEMETHODNAME);
    }

    *//**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法:多条excel的监听
     * @param receiver
     * @return
     *//*
    @Bean
    MessageListenerAdapter listenerAdapterList(Receiver receiver) {
        return new MessageListenerAdapter(receiver, Constant.LISTMETHODNAME);
    }*/

}