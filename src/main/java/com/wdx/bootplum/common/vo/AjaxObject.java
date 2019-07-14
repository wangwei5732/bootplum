package com.wdx.bootplum.common.vo;

import com.wdx.bootplum.common.enums.AjaxCodeEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author wangwei
 * @Description //TODO 统一返回数据格式
 * @Date 15:17 2019-03-13
 * @Param
 * @return
 **/
@Data
public class AjaxObject<T> implements Serializable {

    /**
     * 返回码
     */
    private String code;
    /**
     * 消息内容
     */
    private String msg;

    /**
     * 请求返回的数据
     */
    private T data;


    /**
     * @return com.wdx.bootplum.common.vo.AjaxObject
     * @Author wangwei
     * @Description //TODO 根据ajaxCodeEnum去生成返回值
     * @Date 15:37 2019-03-13
     * @Param [ajaxCodeEnum, data]
     **/
    public static <T> AjaxObject<T> basicAjax(AjaxCodeEnum ajaxCodeEnum, T data) {
        AjaxObject ajaxObject = new AjaxObject();
        ajaxObject.code = ajaxCodeEnum.getCode();
        ajaxObject.msg = ajaxCodeEnum.getName();
        ajaxObject.data = data;
        return ajaxObject;
    }

    /**
     * @return com.wdx.bootplum.common.vo.AjaxObject
     * @Author wangwei
     * @Description //TODO 自定义返回值
     * @Date 15:41 2019-03-13
     * @Param [ajaxCodeEnum, data]
     **/
    public static <T> AjaxObject<T> customAjax(String code, String msg, T data) {
        AjaxObject ajaxObject = new AjaxObject();
        ajaxObject.code = code;
        ajaxObject.msg = msg;
        ajaxObject.data = data;
        return ajaxObject;
    }

    /**
     * @return com.wdx.bootplum.common.vo.AjaxObject
     * @Author wangwei
     * @Description //TODO 成功
     * @Date 15:41 2019-03-13
     * @Param [ajaxCodeEnum, data]
     **/
    public static <T> AjaxObject<T> customOk(String msg, T data) {
        AjaxObject ajaxObject = new AjaxObject();
        ajaxObject.code = AjaxCodeEnum.OK.getCode();
        ajaxObject.msg = msg;
        ajaxObject.data = data;
        if(data == null){
            ajaxObject.data="";
        }
        return ajaxObject;
    }

    /**
     * @return com.wdx.bootplum.common.vo.AjaxObject
     * @Author wangwei
     * @Description //TODO 失败,无data
     * @Date 15:41 2019-03-13
     * @Param [ajaxCodeEnum, data]
     **/
    public static <T> AjaxObject<T> customFail(String msg, T data) {
        AjaxObject ajaxObject = new AjaxObject();
        ajaxObject.code = AjaxCodeEnum.FAIL.getCode();
        ajaxObject.msg = msg;
        ajaxObject.data = data;
        if(data == null){
            ajaxObject.data="";
        }
        return ajaxObject;
    }

}