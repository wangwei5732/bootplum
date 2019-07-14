package com.wdx.bootplum.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * RedisTaskDo redis任务表
 * </p>
 *
 * @author gqc
 * @since 2019-04-22
 */
@Data
public class RedisTaskDo implements Serializable {
    //用户Id
    String userId;
    //用户名
    String userName;
    //任务描述
    String taskDetail;
    //创建时间
    String crateDate;
    //完成时间
    String endDate;
    //总数量
    String amount;
    //完成数量
    String successCount;


    //上传数据
    List dataList;

    @Override
    public String toString() {
        return "本次上传任务信息：{" +
                "用户ID：'" + userId + '\'' +
                ", 用户名：'" + userName + '\'' +
                ", 任务详情：'" + taskDetail + '\'' +
                ", 创建时间：'" + crateDate + '\'' +
                ", 结束时间：'" + endDate + '\'' +
                ", 上传数量：'" + amount + '\'' +
                ", 成功数量：'" + successCount + '\'' +
                '}';
    }
}