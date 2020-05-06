package com.imby.agent.business.plug.service;

import com.imby.common.dto.BaseResponsePackage;
import com.imby.common.dto.HeartbeatPackage;

/**
 * <p>
 * 心跳服务接口
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020年3月4日 下午1:42:57
 */
public interface IHeartbeatService {

    /**
     * <p>
     * 处理心跳包
     * </p>
     *
     * @param heartbeatPackage 心跳包
     * @return {@link BaseResponsePackage}
     * @author 皮锋
     * @custom.date 2020年3月4日 下午1:47:28
     */
    BaseResponsePackage dealHeartbeatPackage(HeartbeatPackage heartbeatPackage);

}