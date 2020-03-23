package com.transfar.server.business.server.service;

import com.transfar.common.dto.ServerPackage;

/**
 * <p>
 * 服务器信息服务层接口
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020/3/23 15:22
 */
public interface IServerService {

    /**
     * <p>
     * 处理服务器信息包
     * </p>
     *
     * @param serverPackage 服务器信息包
     * @return boolean
     * @author 皮锋
     * @custom.date 2020/3/23 15:29
     */
    boolean dealServerPackage(ServerPackage serverPackage);
}