package com.transfar.server.business.server.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.transfar.common.domain.Alarm;
import com.transfar.common.dto.AlarmPackage;
import com.transfar.server.business.server.dao.IMonitorAlarmDefinitionDao;
import com.transfar.server.business.server.domain.TransfarSms;
import com.transfar.server.business.server.entity.MonitorAlarmDefinition;
import com.transfar.server.business.server.service.IAlarmService;
import com.transfar.server.business.server.service.ISmsService;
import com.transfar.server.constant.AlarmWayEnums;
import com.transfar.server.constant.EnterpriseConstants;
import com.transfar.server.property.MonitoringServerWebProperties;
import com.transfar.server.util.AlarmUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 告警服务实现
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020年3月10日 下午1:30:07
 */
@Service
@Slf4j
public class AlarmServiceImpl implements IAlarmService {

    /**
     * 短信服务接口
     */
    @Autowired
    private ISmsService smsService;

    /**
     * 监控配置属性
     */
    @Autowired
    private MonitoringServerWebProperties config;

    /**
     * 监控告警定义数据访问对象
     */
    @Autowired
    private IMonitorAlarmDefinitionDao monitorAlarmDefinitionDao;

    /**
     * <p>
     * 处理告警包
     * </p>
     *
     * @param alarmPackage 告警包
     * @return Boolean
     * @author 皮锋
     * @custom.date 2020年3月10日 下午1:33:55
     */
    @Override
    public Boolean dealAlarmPackage(AlarmPackage alarmPackage) {
        // 获取告警信息
        Alarm alarm = alarmPackage.getAlarm();
        // 处理告警消息
        return this.dealAlarm(alarm);
    }

    /**
     * <p>
     * 处理告警消息
     * </p>
     *
     * @param alarm 告警
     * @return boolean
     * @author 皮锋
     * @custom.date 2020/4/2 11:49
     */
    private boolean dealAlarm(Alarm alarm) {
        // 返回结果
        boolean result = false;
        // 告警开关是否打开
        boolean enable = this.config.getAlarmProperties().isEnable();
        if (!enable) {
            log.warn("告警开关没有打开，不发送告警消息！");
            // 停止往下执行
            return true;
        }
        // 是测试告警信息，不做处理，直接返回
        if (alarm.isTest()) {
            log.warn("当前为测试信息，不发送告警消息！");
            // 停止往下执行
            return true;
        }
        // 告警级别
        String level = alarm.getAlarmLevel().name();
        // 告警代码
        String code = alarm.getCode();
        // 如果有告警代码，查询数据库中此告警代码对应的告警级别，数据库中的告警级别优先级最高
        if (StringUtils.isNotBlank(code)) {
            LambdaQueryWrapper<MonitorAlarmDefinition> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(MonitorAlarmDefinition::getCode, code);
            MonitorAlarmDefinition monitorAlarmDefinition = this.monitorAlarmDefinitionDao.selectOne(lambdaQueryWrapper);
            if (monitorAlarmDefinition != null) {
                String dbLevel = monitorAlarmDefinition.getGrade();
                if (StringUtils.isNotBlank(dbLevel)) {
                    level = dbLevel;
                }
            }
        }
        // 告警级别小于配置的告警级别，不做处理，直接返回
        String configAlarmLevel = this.config.getAlarmProperties().getLevel();
        if (!AlarmUtils.isAlarm(configAlarmLevel, level)) {
            log.warn("小于配置的告警级别，不发送告警消息！");
            // 停止往下执行
            return true;
        }
        // 告警内容
        String msg = alarm.getMsg();
        // 没有告警内容，不做处理，直接返回
        if (StringUtils.isBlank(msg)) {
            log.warn("告警内容为空，不发送告警消息！");
            // 停止往下执行
            return true;
        }
        // 告警内容标题
        String alarmTitle = alarm.getTitle();
        // 告警方式
        String alarmType = this.config.getAlarmProperties().getType();
        // 告警方式为短信告警
        if (StringUtils.equalsIgnoreCase(alarmType, AlarmWayEnums.SMS.name())) {
            // 处理短信告警
            result = this.dealSmsAlarm(alarmTitle, msg, level);
        } else if (StringUtils.equalsIgnoreCase(alarmType, AlarmWayEnums.MAIL.name())) {
            // 处理邮件告警
        }
        return result;
    }

    /**
     * <p>
     * 处理短信告警
     * </p>
     *
     * @param alarmTitle 告警信息标题
     * @param msg        告警信息
     * @param level      告警级别
     * @return boolean
     * @author 皮锋
     * @custom.date 2020年3月10日 下午3:13:35
     */
    private boolean dealSmsAlarm(String alarmTitle, String msg, String level) {
        // 返回结果
        boolean result = false;
        // 短信接口商家
        String enterprise = this.config.getAlarmProperties().getSmsProperties().getEnterprise();
        // 判断短信接口商家，不同的商家调用不同的接口
        if (StringUtils.equalsIgnoreCase(EnterpriseConstants.TRANSFAR, enterprise)) {
            // 调用创发短信接口
            result = this.callTransfarSmsApi(alarmTitle, msg, level);
        }
        return result;
    }

    /**
     * <p>
     * 封装数据，调用创发公司的短信接口发送短信
     * </p>
     *
     * @param alarmTitle 告警内容标题
     * @param msg        告警内容
     * @param level      告警级别
     * @return boolean
     * @author 皮锋
     * @custom.date 2020年3月10日 下午3:19:36
     */
    private boolean callTransfarSmsApi(String alarmTitle, String msg, String level) {
        String[] phones = this.config.getAlarmProperties().getSmsProperties().getPhoneNumbers();
        String enterprise = this.config.getAlarmProperties().getSmsProperties().getEnterprise();
        TransfarSms transfarSms = TransfarSms.builder()//
                .content(StringUtils.isBlank(alarmTitle) ? msg : ("[" + alarmTitle + "]" + msg))//
                .type(level)//
                .phone(TransfarSms.getPhones(phones))//
                .identity(enterprise)//
                .build();
        // 创发公司短信接口
        String str = this.smsService.sendSmsByTransfarApi(transfarSms);
        // 短信发送成功
        return (!StringUtils.equalsIgnoreCase("null", str)) && StringUtils.isNotBlank(str);
    }

}
