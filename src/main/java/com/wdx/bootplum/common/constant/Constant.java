package com.wdx.bootplum.common.constant;

/**
 * <p>
 * Constant Redis 发布配置
 * </p>
 *
 * @author gqc
 * @since 2019-04-17
 */
public interface Constant {

    // 未通过格式校验的数据
    String FAILLISTKEY = "excelToDB:failListKey";
    // 已通过格式校验的数据
    String SUCCESSLISTKEY = "excelToDB:successListKey";
    // 未通过格式校验的数据大小
    String FAILSIZEKEY = "excelToDB:failSizeKey";
    // 已通过格式校验的数据大小
    String SUCCSIZEKEY = "excelToDB:succSizeKey";
    // 消息队列中未被消费的数据大小
    String SUCCSIZETEMPKEY = "excelToDB:succSizeTempKey";
    // 导入数据库失败的数据大小
    String FAILTODBKEY = "excelToDB:failToDBKey";


    // redis中，发布者中所使用到的频道名称,根据自己模块添加频道名称
    String RECEIVESINGLE = "excelToDB:receiveSingleDemo";
    String RECEIVELIST = "excelToDB:receiveListDemo";

    // redis中，消费者的方法名,根据自己模块添加方法名称
    String SINGLEMETHODNAME = "receiveSingleDemo";
    String LISTMETHODNAME = "receiveListDemo";


    //redis临时任务表
    String TASK_EXCELTODB = "task:excelToDB";

    //任务常量-导入excel文件
    String TASK_EXCEL = "导入EXCEL文件";

}
