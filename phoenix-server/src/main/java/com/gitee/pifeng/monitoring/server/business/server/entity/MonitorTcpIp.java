package com.gitee.pifeng.monitoring.server.business.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * TCP/IP信息表
 * </p>
 *
 * @author 皮锋
 * @custom.date 2022/1/11 16:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("MONITOR_TCPIP")
public class MonitorTcpIp {

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * IP地址（来源）
     */
    @TableField("IP_SOURCE")
    private String ipSource;

    /**
     * IP地址（目的地）
     */
    @TableField("IP_TARGET")
    private String ipTarget;

    /**
     * 端口号
     */
    @TableField("PORT_TARGET")
    private Integer portTarget;

    /**
     * 描述
     */
    @TableField("DESCR")
    private String descr;

    /**
     * 协议
     */
    @TableField("PROTOCOL")
    private String protocol;

    /**
     * 状态（0：不通，1：正常）
     */
    @TableField("STATUS")
    private String status;

    /**
     * 离线次数
     */
    @TableField("OFFLINE_COUNT")
    private Integer offlineCount;

    /**
     * 新增时间
     */
    @TableField("INSERT_TIME")
    private Date insertTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}