package com.wdx.bootplum.common.exception;


import com.wdx.bootplum.common.enums.AjaxCodeEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @Author wangwei
 * @Description //TODO 自定义异常
 * @Date 16:40 2019-03-13
 * @Param
 * @return
 **/
@Data
public class BusinessApiException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String msg;

	public BusinessApiException(String code, String msg) {
		super(msg);
		this.msg = msg;
		this.code=code;
	}
	public BusinessApiException(AjaxCodeEnum ajaxCodeEnum) {
		super(ajaxCodeEnum.getName());
		this.code=ajaxCodeEnum.getCode();
		this.msg = ajaxCodeEnum.getName();
	}

}