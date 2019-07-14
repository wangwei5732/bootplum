package com.wdx.bootplum.common.redis;

import com.wdx.bootplum.common.constant.Constant;
import com.wdx.bootplum.common.utils.DateTimeUtils;
import com.wdx.bootplum.common.utils.ExcelCommUtil;
import com.wdx.bootplum.common.utils.JsonUtil;
import com.wdx.bootplum.system.entity.ExcelModelDemo;
import com.wdx.bootplum.system.entity.RedisTaskDo;
import com.wdx.bootplum.system.service.IExcelService;
import com.wdx.bootplum.system.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * Receiver
 * </p>
 *
 * @author gqc
 * @since 2019-04-17
 */
@Service
public class Receiver {

    @Autowired
    ExcelCommUtil excelImportCommUtil;

    @Autowired
    IExcelService iExcelService;

    @Autowired
    RedisDao redisDao;

    /**
     * 用于接收对象集合，将集合批量存储
     *
     * @param map
     */
    public void receiveListDemo(String message) throws IOException {

        // 将json字符串转换成对象
        RedisTaskDo redisTaskDo = JsonUtil.stringToBean(message, RedisTaskDo.class);
        List<ExcelModelDemo> list = redisTaskDo.getDataList();
        //方式一：批量插入
        iExcelService.saveBatch(list);
        redisTaskDo.setEndDate(DateTimeUtils.getCurrentNowDate());
        //任务完成后推送任务结果
        WebSocketServer.SendToUserMessage(redisTaskDo.getUserName(),redisTaskDo.toString());
        //方式二：遍历集合,并依次将其发布
//        for (ExcelModelDemo excelModelDemo : list) {
//            redisDao.publish(Constant.receiveSingle, excelModelDemo);
//        }

    }

    /**
     * @Description: 用于接收单个对象，将对象同步至数据库，如果同步失败，则存入redis中
     * @Param: [message] “fastjson”转换后的json字符串
     * @Retrun: void
     */
    public void receiveSingleDemo(String message) {
        // 将json字符串转换成实体对象
        ExcelModelDemo excelModelDemo = JsonUtil.stringToBean(message, ExcelModelDemo.class);
        // 尝试同步数据库并返回同步结果
        boolean result = iExcelService.save(excelModelDemo);
        if (!result) {
            // 同步失败，将其存入redis中
            redisDao.leftPushKey(Constant.FAILTODBKEY, excelModelDemo);
        }
        // 加上-1，其实也就是做减1操作
        redisDao.incrOrDecr(Constant.SUCCSIZETEMPKEY, -1);
    }

}

