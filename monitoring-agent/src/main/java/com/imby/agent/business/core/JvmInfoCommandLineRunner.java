package com.imby.agent.business.core;

import com.imby.common.dto.BaseResponsePackage;
import com.imby.common.dto.JvmPackage;
import com.imby.common.property.MonitoringProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 在容器启动的时候定时向服务端发Java虚拟机信息包
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020/8/15 22:00
 */
@Slf4j
@Component
@Order(3)
public class JvmInfoCommandLineRunner implements CommandLineRunner, DisposableBean {

    /**
     * 监控配置属性
     */
    @Autowired
    private MonitoringProperties monitoringProperties;

    /**
     * 延迟/周期执行线程池
     */
    private final ScheduledExecutorService seService = new ScheduledThreadPoolExecutor(5,
            new BasicThreadFactory.Builder()
                    // 设置线程名
                    .namingPattern("monitoring-jvm-pool-thread-%d")
                    // 设置为守护线程
                    .daemon(true)
                    .build());

    /**
     * <p>
     * 如果监控配置文件中配置了向服务端发送Java虚拟机信息，则项目启动完成后延迟15秒钟启动定时任务，定时向服务端发送Java虚拟机信息包，
     * 定时任务的执行频率为监控配置文件中配置的Java虚拟机信息包发送频率。
     * </p>
     *
     * @param args 传入的主方法参数
     * @author 皮锋
     * @custom.date 2020/8/15 22:03
     */
    @Override
    public void run(String... args) {
        // 是否发送Java虚拟机信息
        boolean jvmInfoEnable = this.monitoringProperties.getMonitoringJvmInfoProperties().isEnable();
        if (jvmInfoEnable) {
            this.seService.scheduleAtFixedRate(new JvmInfoScheduledExecutor(), 10,
                    this.monitoringProperties.getMonitoringJvmInfoProperties().getRate(), TimeUnit.SECONDS);
        }
    }

    /**
     * <p>
     * 在spring容器销毁时关闭线程池
     * </p>
     * 关闭线程池：monitoring-jvm-pool-thread
     *
     * @author 皮锋
     * @custom.date 2020/8/15 22:05
     */
    @Override
    public void destroy() {
        if (!this.seService.isShutdown()) {
            this.seService.shutdown();
            log.info("延迟/周期执行线程池“monitoring-jvm-pool-thread”已经关闭！");
        }
    }

}

/**
 * <p>
 * Java虚拟机信息包调度程序执行器
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020年3月8日 下午2:48:10
 */
@Slf4j
class JvmInfoScheduledExecutor implements Runnable {

    /**
     * <p>
     * 发送Java虚拟机信息包
     * </p>
     *
     * @author 皮锋
     * @custom.date 2020/8/15 22:08
     */
    @Override
    public void run() {
        JvmPackage jvmPackage = new PackageConstructor().structureJvmPackage();
        // 向服务端发送Java虚拟机信息包
        BaseResponsePackage baseResponsePackage = MethodExecuteHandler.sendJvmPackage2Server(jvmPackage);
        log.info("Java虚拟机包响应消息：{}", baseResponsePackage.toJsonString());
    }

}
