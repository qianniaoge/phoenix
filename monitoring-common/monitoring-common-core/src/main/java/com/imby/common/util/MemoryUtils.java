package com.imby.common.util;

import cn.hutool.core.io.unit.DataSizeUtil;
import com.imby.common.domain.server.MemoryDomain;
import com.imby.common.init.InitSigar;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;

/**
 * <p>
 * 内存工具类
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020/8/13 11:35
 */
public class MemoryUtils extends InitSigar {

    /**
     * <p>
     * 获取内存信息
     * </p>
     *
     * @return {@link MemoryDomain}
     * @throws SigarException Sigar异常
     * @author 皮锋
     * @custom.date 2020年3月3日 下午2:24:41
     */
    public static MemoryDomain getMemoryInfo() throws SigarException {
        Mem mem = SIGAR.getMem();
        return new MemoryDomain()
                .setMemTotal(DataSizeUtil.format(mem.getTotal()))
                .setMemUsed(DataSizeUtil.format(mem.getUsed()))
                .setMemFree(DataSizeUtil.format(mem.getFree()))
                .setMenUsedPercent(String.format("%.2f", mem.getUsedPercent()) + "%");
    }
}