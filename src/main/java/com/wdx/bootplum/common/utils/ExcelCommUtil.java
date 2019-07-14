package com.wdx.bootplum.common.utils;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.wdx.bootplum.common.config.BootPlumConfig;
import com.wdx.bootplum.common.constant.Constant;
import com.wdx.bootplum.common.redis.RedisDao;
import com.wdx.bootplum.common.vo.AjaxObject;
import com.wdx.bootplum.system.entity.RedisTaskDo;
import com.wdx.bootplum.system.entity.SysUserDO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ExcelImportCommUtil 公共的导入excel工具类 + redis消息发布
 * </p>
 *
 * @author gqc
 * @since 2019-04-18
 */
@Component
public class ExcelCommUtil {

    @Autowired
    private BootPlumConfig bootPlumConfig;

    @Autowired
    RedisDao redisDao;


    /**
     * 返回结果
     */
    ExcelImportResult excelImportResult = null;

    /**
     * 保存上传数据
     *
     * @param request
     * @param multipartFile
     * @param pojoClass
     * @return
     * @throws IOException
     */
    public AjaxObject ImportExcelByDo(HttpServletRequest request, MultipartFile multipartFile, Class<?> pojoClass) throws IOException {
        //1.校验文件类型
        if (!verifyFile(multipartFile, pojoClass)) {
            return AjaxObject.customFail("请上传excel文件", null);
        }
        //saveBatch(excelImportResult.getList());
        // 清空redis中的部分旧数据
        cleanCache();
        // 将参数result中的部分数据存入redis中，并把格式校验成功的数据发布至对应频道中
        cacheAndPublish(excelImportResult);
        //2.把格式校验成功的数据发布至对应频道中,导入数据库

        //3.返回客户端信息
        return returnMsg(request,pojoClass);
    }


    /**
     * 校验文件类型，解析上传数据
     *
     * @return
     */
    private boolean verifyFile(MultipartFile multipartFile, Class<?> pojoClass) throws IOException {
        //获取文件名+文件后缀
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.indexOf("."));
        if (!".xls".equals(suffix) && !".xlsx".equals(suffix)) {
            return false;
        }
        //获取导入数据的返回结果
        excelImportResult = EasyPoiUtil.importExcel(multipartFile.getInputStream(), 1, 1, pojoClass);
        return true;
    }

    /**
     * 返回上传信息
     *
     * @return
     */
    private AjaxObject returnMsg(HttpServletRequest request,Class<?> pojoClass) throws IOException {
        Map<String, String> map = new HashMap<>();
        //成功条数
        int successSize = excelImportResult.getList().size();
        //失败条数
        int failSize = excelImportResult.getFailList().size();

        if (failSize > 0) {
            //返回错误记录文件，提供客户端下载
            //基准URL
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            //文件名称
            String fileName = DateTimeUtils.getCurrentDateTimeStr() + "_error" + ".xls";
            //保存文件到本地
//            EasyPoiUtil.saveLocal(bootPlumConfig.getUploadPath(), fileName,excelImportResult.getFailList(),pojoClass);
            EasyPoiUtil.saveLocalByWorkbook(bootPlumConfig.getUploadPath(), fileName, excelImportResult.getFailWorkbook());
            //文件地址
            map.put("path", basePath + "/static/file/" + fileName);
        }
        //返回信息
        map.put("successSize", String.valueOf(successSize));
        map.put("failSize", String.valueOf(failSize));
        if (failSize > 0) {
            return AjaxObject.customFail("存在错误数据", map);
        }
        return AjaxObject.customOk("数据正确", map);
    }


    /**
     * 清空redis中的部分旧数据
     */
    @Transactional
    public void cleanCache() {
        List<String> keyList = new ArrayList<>(10);
        keyList.add(Constant.FAILTODBKEY);
        keyList.add(Constant.SUCCSIZETEMPKEY);
        keyList.add(Constant.FAILLISTKEY);
        keyList.add(Constant.FAILSIZEKEY);
        keyList.add(Constant.SUCCSIZEKEY);
        redisDao.cleanCache(keyList);
    }


    /**
     * @Description: 把格式校验成功的数据发布至对应频道中
     * @Param: [result]
     * @Retrun: void
     */
    @Transactional
    public void cacheAndPublish(ExcelImportResult result) {
        // 通过校验的数据
        List successList = result.getList();
        // 未通过校验的数据
        List failList = result.getFailList();
        //
        int successSize = successList.size();
        int failSize = failList.size();
        // 将未通过校验的数据存入redis中
        redisDao.setStringKey(Constant.FAILLISTKEY, failList);
        redisDao.setStringKey(Constant.FAILSIZEKEY, failSize);
        // 将通过校验的数据存入redis中
        redisDao.setStringKey(Constant.SUCCSIZEKEY, successSize);
        // succSizeTempKey 用于判断消息队列中未消费数据的大小
        redisDao.setStringKey(Constant.SUCCSIZETEMPKEY, successSize);

        //创建Tasks数据
        SysUserDO sysUserDO = (SysUserDO) SecurityUtils.getSubject().getPrincipal();
        //
        RedisTaskDo redisTaskDo = new RedisTaskDo();
        redisTaskDo.setUserId(sysUserDO.getUserId());
        redisTaskDo.setUserName(sysUserDO.getUsername());
        redisTaskDo.setTaskDetail(Constant.TASK_EXCEL);
        redisTaskDo.setCrateDate(DateTimeUtils.getCurrentNowDate());
        redisTaskDo.setAmount(String.valueOf(successSize + failSize));
        redisTaskDo.setSuccessCount(String.valueOf(successSize));
        //保存任务信息到redis数据库中
        redisDao.setStringKey(Constant.TASK_EXCELTODB, redisTaskDo);
        //存入数据
        redisTaskDo.setDataList(successList);
        // 发布消息
        redisDao.publish(Constant.RECEIVELIST, redisTaskDo);
    }

    /**
     * 根据key值，返回redis中对应的结果
     *
     * @param key
     * @return
     */
    public long getTempSize(String key) {
        return redisDao.getStringValue(key, long.class);
    }


}