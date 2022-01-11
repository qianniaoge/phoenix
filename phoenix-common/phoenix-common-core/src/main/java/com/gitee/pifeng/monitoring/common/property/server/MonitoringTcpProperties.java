package com.gitee.pifeng.monitoring.common.property.server;

import lombok.*;

/**
 * <p>
 * TCP配置属性
 * </p>
 *
 * @author 皮锋
 * @custom.date 2022/1/11 16:33
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MonitoringTcpProperties {

    /**
     * 是否监控网络
     */
    private boolean enable;

}
