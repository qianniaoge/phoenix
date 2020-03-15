package com.transfar.annotation;

import com.transfar.selector.EnableMonitoringPlugSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 激活监控功能
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020/3/13 15:02
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
// 导入注册springboot类
@Import({EnableMonitoringPlugSelector.class})
@Documented
public @interface EnableMonitoringPlug {

    /**
     * <p>
     * 设置配置文件路径<br>
     * 例如：configFilePath = "classpath:conf/"
     * </p>
     *
     * @return 配置文件路径
     * @author 皮锋
     * @custom.date 2020/3/15 21:18
     */
    String configFilePath() default "";

    /**
     * <p>
     * 设置配置文件名字<br>
     * 例如：configFileName = "monitoring.properties"
     * </p>
     *
     * @return 配置文件名字
     * @author 皮锋
     * @custom.date 2020/3/15 21:18
     */
    String configFileName() default "";

}
