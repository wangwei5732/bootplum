package com.wdx.bootplum.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
/**
 * <p>
 * 定时任务表
 * </p>
 *
 * @author yangyibin
 * @since 2019-04-10
 */
@Data
@TableName("sys_task")
public class SysTaskDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(groups = {SysTaskDO.update.class}, message = "id不能为空")
    @TableId
    private String Id;
    /**
     * cron表达式
     */
    @NotBlank(groups = {SysTaskDO.update.class,SysTaskDO.add.class}, message = "cron表达式不能为空")
    @ApiModelProperty(notes = "cron表达式")
    private String cronExpression;

    /**
     * 任务调用的方法名
     */
    @NotBlank(groups = {SysTaskDO.update.class,SysTaskDO.add.class}, message = "任务调用的方法名不能为空")
    @ApiModelProperty(notes = "任务调用的方法名")
    private String methodName;

    /**
     * 任务是否有状态
     */
    @NotBlank(groups = {SysTaskDO.update.class,SysTaskDO.add.class}, message = "任务是否有状态不能为空")
    @ApiModelProperty(notes = "任务是否有状态")
    private String isConcurrent;

    /**
     * 任务描述
     */
    @NotBlank(groups = {SysTaskDO.update.class,SysTaskDO.add.class}, message = "任务描述不能为空")
    @ApiModelProperty(notes = "任务描述")
    private String description;

    /**
     * 更新者
     */
    @ApiModelProperty(notes = "更新者")
    private String updateBy;

    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    @NotBlank(groups = {SysTaskDO.update.class,SysTaskDO.add.class}, message = "任务执行时调用哪个类的方法 包名+类名不能为空")
    @ApiModelProperty(notes = "任务执行时调用哪个类的方法 包名+类名")
    private String beanClass;

    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createDate;

    /**
     * 任务状态
     */
    @NotBlank(groups = {SysTaskDO.update.class,SysTaskDO.add.class}, message = "任务状态不能为空")
    @ApiModelProperty(notes = "任务状态")
    private String jobStatus;

    /**
     * 任务分组
     */
    @NotBlank(groups = {SysTaskDO.update.class,SysTaskDO.add.class}, message = "任务分组不能为空")
    @ApiModelProperty(notes = "任务分组")
    private String jobGroup;

    /**
     * 更新时间
     */
    @ApiModelProperty(notes = "更新时间")
    private LocalDateTime updateDate;

    /**
     * 创建者
     */
    @ApiModelProperty(notes = "创建者")
    private String createBy;

    /**
     * Spring bean
     */
    @NotBlank(groups = {SysTaskDO.update.class,SysTaskDO.add.class}, message = "Spring bean不能为空")
    @ApiModelProperty(notes = "Spring bean")
    private String springBean;

    /**
     * 任务名
     */
    @NotBlank(groups = {SysTaskDO.update.class,SysTaskDO.add.class}, message = "任务名不能为空")
    @ApiModelProperty(notes = "任务名")
    private String jobName;

    public interface add {
    }
    public interface update {
    }
}
