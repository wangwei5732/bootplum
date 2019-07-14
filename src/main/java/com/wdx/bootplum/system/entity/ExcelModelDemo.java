package com.wdx.bootplum.system.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * ExcelModelDemo
 * </p>
 *
 * @author gqc
 * @Desciption
 * @since 2019-04-12
 */
@TableName("excel_demo")
@Data
public class ExcelModelDemo implements IExcelModel, IExcelDataModel{
    @Excel(name = "姓名")
    private String name;

    @Excel(name = "年龄")
   // @Max(value = 25, message = "年龄不能超过25岁")
    private Integer age;

    @Excel(name = "性别", replace = {"男_1", "女_2"})
    private String sex;

    @Excel(name = "地址", isImportField = "true")
    @NotBlank(message = "address不能为空")
    private String address;

    //错误信息
    @TableField(exist = false)
    private String errorMsg;

    //行号
    @TableField(exist = false)
    private int rowNum;


    public ExcelModelDemo(String name, Integer age, String sex, String address) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }

    public ExcelModelDemo() {
    }

    @Override
    public String toString() {
        return "\nExcelModelDemo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", rowNum=" + rowNum +
                '}';
    }
}