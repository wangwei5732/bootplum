package com.wdx.bootplum.system.websocket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: wangwei
 * @Date: 2019-04-16 10:13
 * @Description: usernameWithToken为username+"_"+token的拼接，解决的是保证同一用户多地登录时可以接收到消息
 */
@ServerEndpoint(value = "/ws/{usernameWithToken}")
@Component
public class WebSocketServer {
    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "usernameWithToken") String usernameWithToken, Session session) {
        sessionMap.put(usernameWithToken, session);
        // 在线数加1
        int cnt = OnlineCount.incrementAndGet();
        log.info("有连接加入，当前连接数为：{}", cnt);
        SendMessage(session, "连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value = "usernameWithToken") String usernameWithToken, Session session) {
        sessionMap.remove(usernameWithToken);
        if (OnlineCount.intValue() > 0) {
            int cnt = OnlineCount.decrementAndGet();
            log.info("有连接关闭，当前连接数为：{}", cnt);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}", message);
        SendMessage(session, "收到消息，消息内容：" + message);

    }

    /**
     * 出现错误
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}", error.getMessage(), session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     *
     * @param session
     * @param message
     */
    public static void SendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     *
     * @param message
     * @throws IOException
     */
    public static void BroadCastInfo(String message) throws IOException {
        Set<String> set = sessionMap.keySet();
        for (String key : set) {
            Session session = sessionMap.get(key);
            SendMessage(session, message);
        }
    }

    /**
     * 指定Session发送消息
     *
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void SendMessage(String sessionId, String message) throws IOException {
        Session session = null;
        Set<String> set = sessionMap.keySet();
        for (String key : set) {
            Session s = sessionMap.get(key);
            if (s.getId().equals(sessionId)) {
                session = s;
                break;
            }
        }
        if (session != null) {
            SendMessage(session, message);
        } else {
            log.warn("没有找到你指定ID的会话：{}", sessionId);
        }
    }

    /**
     * @return void
     * @Author wangwei
     * @Description //TODO 指定username发送消息
     * @Date 16:24 2019-04-16
     * @Param [userName, message]
     **/
    public static void SendToUserMessage(String userName, String message) throws IOException {
        userName = userName + "_";
        Set<String> set = sessionMap.keySet();
        for (String key : set) {
            if (StringUtils.contains(key, userName)) {
                Session session = sessionMap.get(key);
                SendMessage(session, message);
            }
        }
    }

}
