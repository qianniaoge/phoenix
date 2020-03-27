package com.transfar.server.business.server.domain;

import com.transfar.common.abs.SuperBean;
import com.transfar.common.domain.server.CpuDomain;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * <p>
 * 服务器CPU
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020/3/27 14:55
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Cpu extends SuperBean {

    /**
     * CPU过载90%次数
     */
    private int num90;

    /**
     * CPU过载100%次数
     */
    private int num100;

    /**
     * CPU信息获取频率
     */
    private long rate;

    /**
     * 是否已经发送过CPU过载90%告警消息
     */
    private boolean isAlarm90;

    /**
     * 是否确认CPU过载90%
     */
    private boolean isOverLoad90;

    /**
     * 是否已经发送过CPU过载100%告警消息
     */
    private boolean isAlarm100;

    /**
     * 是否确认CPU过载100%
     */
    private boolean isOverLoad100;

    /**
     * 平均CPU使用率
     */
    private double avgCpuCombined;

    /**
     * IP地址
     */
    private String ip;

    /**
     * CPU信息
     */
    private CpuDomain cpuDomain;

    /**
     * <p>
     * 计算平均CPU使用率
     * </p>
     *
     * @param cpuDomain CPU信息
     * @return 平均CPU使用率
     * @author 皮锋
     * @custom.date 2020/3/27 15:45
     */
    public static double calculateAvgCpuCombined(CpuDomain cpuDomain) {
        List<CpuDomain.CpuInfoDomain> cpuInfoDomains = cpuDomain.getCpuList();
        // 和
        double sum = 0;
        for (CpuDomain.CpuInfoDomain cpuInfoDomain : cpuInfoDomains) {
            double num = 0;
            String cpuCombined = cpuInfoDomain.getCpuCombined();
            if (StringUtils.isNotBlank(cpuCombined)) {
                num = Double.parseDouble(cpuCombined.substring(0, cpuCombined.length() - 1));
            }
            sum += num;
        }
        return sum / cpuInfoDomains.size();
    }

}
