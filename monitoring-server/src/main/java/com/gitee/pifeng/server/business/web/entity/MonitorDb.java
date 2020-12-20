package com.gitee.pifeng.server.business.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据库表
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020-12-19
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MONITOR_DB")
@ApiModel(value = "MonitorDb对象", description = "数据库表")
public class MonitorDb implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "数据库URL")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "用户名")
    @TableField("USERNAME")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "数据库类型")
    @TableField("DB_TYPE")
    private String dbType;

    @ApiModelProperty(value = "驱动类")
    @TableField("DRIVER_CLASS")
    private String driverClass;

    @ApiModelProperty(value = "描述")
    @TableField("DB_DESC")
    private String dbDesc;

    @ApiModelProperty(value = "数据库状态（0：离线，1：在线）")
    @TableField("IS_ONLINE")
    private String isOnline;

    @ApiModelProperty(value = "是否已经断连告警（0：否，1：是）")
    @TableField("IS_ALARM")
    private String isAlarm;

    @ApiModelProperty(value = "插入时间")
    @TableField("INSERT_TIME")
    private Date insertTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

}
