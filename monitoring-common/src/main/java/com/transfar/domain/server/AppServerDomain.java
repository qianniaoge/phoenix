package com.transfar.domain.server;

import java.util.Date;

import com.transfar.common.SuperBean;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 应用服务器信息
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020/3/3 13:43
 */
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AppServerDomain extends SuperBean {
	/**
	 * 服务器IP
	 */
	private String serverIP;
	/**
	 * 服务器Url
	 */
	private String serverURL;
	/**
	 * 服务器类型
	 */
	private String serverType;
	/**
	 * 服务器时间
	 */
	private Date serverTime;

	@Override
	public String toString() {
		return "AppServerVo [服务器IP=" + serverIP + ", 服务器Url=" + serverURL + ", 服务器类型=" + serverType + ", 服务器时间="
				+ serverTime + "]";
	}

}
