package com.wdx.bootplum.system.websocket;

import com.wdx.bootplum.common.vo.AjaxObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Auther: wangwei
 * @Date: 2019-04-16 10:23
 * @Description: websocket测试controller
 */
@RestController
@RequestMapping("/ws/test")
public class WebSocketTestController {
    /**
     * @Author wangwei
     * @Description //TODO 群发消息
     * @Date 10:25 2019-04-16
     * @Param [message]
     * @return java.lang.String
     **/
    @GetMapping("/sendAll")
    AjaxObject sendAllMessage(@RequestParam String message){
        try {
            WebSocketServer.BroadCastInfo(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxObject.customOk("发送成功",null);
    }
    /**
     * @Author wangwei
     * @Description //TODO 向某一用户发送消息，可以暴露给外部实现聊天
     * @Date 10:25 2019-04-16
     * @Param [message]
     * @return java.lang.String
     **/
    @GetMapping("/sendToOne")
    String sendToOne(@RequestParam String message , @RequestParam String sessionId){
        try {
            WebSocketServer.SendMessage(sessionId , message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
    /**
     * @Author wangwei
     * @Description //TODO 向某一用户发送消息,系统内部使用，尽量不暴露给外部接口
     * @Date 10:25 2019-04-16
     * @Param [message]
     * @return java.lang.String
     **/
    @GetMapping("/sendToUserMessage")
    String SendToUserMessage(@RequestParam String message , @RequestParam String userName){
        try {
            WebSocketServer.SendToUserMessage(userName , message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

}
