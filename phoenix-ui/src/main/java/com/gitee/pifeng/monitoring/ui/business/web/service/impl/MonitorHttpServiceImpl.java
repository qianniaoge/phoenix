package com.gitee.pifeng.monitoring.ui.business.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.pifeng.monitoring.ui.business.web.dao.IMonitorHttpDao;
import com.gitee.pifeng.monitoring.ui.business.web.entity.MonitorHttp;
import com.gitee.pifeng.monitoring.ui.business.web.service.IMonitorHttpService;
import com.gitee.pifeng.monitoring.ui.business.web.vo.MonitorHttpVo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * HTTP信息服务实现类
 * </p>
 *
 * @author 皮锋
 * @since 2022-04-11
 */
@Service
public class MonitorHttpServiceImpl extends ServiceImpl<IMonitorHttpDao, MonitorHttp> implements IMonitorHttpService {

    /**
     * <p>
     * 获取HTTP列表
     * </p>
     *
     * @param current        当前页
     * @param size           每页显示条数
     * @param hostnameSource 主机名（来源）
     * @param urlTarget      URL地址（目的地）
     * @param method         请求方法
     * @param status         状态
     * @return layUiAdmin响应对象
     * @author 皮锋
     * @custom.date 2022/4/11 10:51
     */
    @Override
    public Page<MonitorHttpVo> getMonitorHttpList(Long current, Long size, String hostnameSource, String urlTarget, String method, Integer status) {
        // 查询数据库
        IPage<MonitorHttp> ipage = new Page<>(current, size);
        LambdaQueryWrapper<MonitorHttp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(hostnameSource)) {
            lambdaQueryWrapper.like(MonitorHttp::getHostnameSource, hostnameSource);
        }
        if (StringUtils.isNotBlank(urlTarget)) {
            lambdaQueryWrapper.like(MonitorHttp::getUrlTarget, urlTarget);
        }
        if (StringUtils.isNotBlank(method)) {
            lambdaQueryWrapper.eq(MonitorHttp::getMethod, method);
        }
        if (null != status) {
            // -1 用来表示状态未知
            if (-1 == status) {
                // 状态为 null 或 空字符串
                lambdaQueryWrapper.and(wrapper -> wrapper.isNull(MonitorHttp::getStatus));
            }
            // -2 表示其它
            else if (-2 == status) {
                lambdaQueryWrapper.and(wrapper -> wrapper
                        .ne(MonitorHttp::getStatus, 200)
                        .ne(MonitorHttp::getStatus, 500)
                        .ne(MonitorHttp::getStatus, 404)
                        .ne(MonitorHttp::getStatus, 403)
                        .ne(MonitorHttp::getStatus, 400)
                        .isNotNull(MonitorHttp::getStatus));
            } else {
                lambdaQueryWrapper.eq(MonitorHttp::getStatus, status);
            }
        }
        IPage<MonitorHttp> monitorHttpPage = this.baseMapper.selectPage(ipage, lambdaQueryWrapper);
        List<MonitorHttp> monitorHttps = monitorHttpPage.getRecords();
        // 转换成HTTP信息表现层对象
        List<MonitorHttpVo> monitorHttpVos = Lists.newLinkedList();
        for (MonitorHttp monitorHttp : monitorHttps) {
            MonitorHttpVo monitorHttpVo = MonitorHttpVo.builder().build().convertFor(monitorHttp);
            monitorHttpVos.add(monitorHttpVo);
        }
        // 设置返回对象
        Page<MonitorHttpVo> monitorHttpVoPage = new Page<>();
        monitorHttpVoPage.setRecords(monitorHttpVos);
        monitorHttpVoPage.setTotal(monitorHttpPage.getTotal());
        return monitorHttpVoPage;
    }
}