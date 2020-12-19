package com.gitee.pifeng.server.business.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.pifeng.common.inf.ISuperBean;
import com.gitee.pifeng.server.business.web.entity.MonitorDb;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * <p>
 * 数据库信息表现层对象
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020/12/19 17:23
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "数据库信息表现层对象")
public class MonitorDbVo implements ISuperBean {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "数据库URL")
    private String url;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "数据库类型")
    private String dbType;

    @ApiModelProperty(value = "描述")
    private String dbDesc;

    @ApiModelProperty(value = "数据库状态（0：离线，1：在线）")
    private String isOnline;

    @ApiModelProperty(value = "插入时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date insertTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * <p>
     * MonitorDbVo转MonitorDb
     * </p>
     *
     * @return {@link MonitorDb}
     * @author 皮锋
     * @custom.date 2020/12/20 9:20
     */
    public MonitorDb convertTo() {
        MonitorDb monitorDb = MonitorDb.builder().build();
        BeanUtils.copyProperties(this, monitorDb);
        return monitorDb;
    }

    /**
     * <p>
     * MonitorDb转MonitorDbVo
     * </p>
     *
     * @param monitorDb {@link MonitorDb}
     * @return {@link MonitorDbVo}
     * @author 皮锋
     * @custom.date 2020/12/20 9:22
     */
    public MonitorDbVo convertFor(MonitorDb monitorDb) {
        BeanUtils.copyProperties(monitorDb, this);
        return this;
    }

}
