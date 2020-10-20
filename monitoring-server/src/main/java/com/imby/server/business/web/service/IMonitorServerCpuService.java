package com.imby.server.business.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imby.server.business.web.entity.MonitorServerCpu;
import com.imby.server.business.web.vo.ServerDetailPageServerCpuVo;

import java.util.List;

/**
 * <p>
 * 服务器CPU服务类
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020/9/4 20:50
 */
public interface IMonitorServerCpuService extends IService<MonitorServerCpu> {

    /**
     * <p>
     * 获取服务器详情页面服务器CPU信息
     * </p>
     *
     * @param ip   服务器IP地址
     * @param time 时间
     * @return 服务器详情页面服务器CPU信息表现层对象
     * @author 皮锋
     * @custom.date 2020/10/19 14:22
     */
    List<ServerDetailPageServerCpuVo> getServerDetailPageServerCpu(String ip, String time);
}
